package com.securoserv.data.utils

import android.util.Log
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.net.UnknownHostException

class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    private val _original by lazy {
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    companion object {
        fun create() = RxErrorHandlingCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val wrapped = _original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        return RxCallAdapterWrapper(retrofit, wrapped)
    }

    private class RxCallAdapterWrapper<R>(
        val _retrofit: Retrofit,
        val _wrappedCallAdapter: CallAdapter<R, *>
    ) : CallAdapter<R, Any> {

        override fun responseType() = _wrappedCallAdapter.responseType()

        override fun adapt(call: Call<R>): Any {
            val adapted = (_wrappedCallAdapter.adapt(call) as Single<R>)
            return adapted.onErrorResumeNext { throwable: Throwable ->
                //need return here
                Single.error(asRetrofitException(throwable))
            }
        }

        private fun asRetrofitException(throwable: Throwable): Errors {
            Log.v("RxCallAdapterWrapper", "Exception is :  ${throwable.message}")
            if (throwable is HttpException) {
                val apiResponse: ApiResponse<*> =
                    Gson().fromJson(
                        throwable.response()!!.errorBody()?.string(),
                        ApiResponse::class.java
                    )
                return when (throwable.code()) {
                    400 -> {
                        Errors.CustomErrors(apiResponse.status)
                    }
                    401 -> {
                        Errors.InvalidToken(apiResponse.error?.message!!)
                    }
                    in 402..499 -> {
                        Errors.GeneralError(apiResponse.error?.message!!, throwable.code())
                    }
                    in 500..599 -> {
                        Errors.ServerError
                    }
                    else -> {
                        Errors.UnExcepted
                    }
                }
            }

            if (throwable is IOException) {
                return Errors.NetworkError
            }

            if (throwable is UnknownHostException) {
                return Errors.NetworkError
            }

            return Errors.UnExcepted
        }
    }
}