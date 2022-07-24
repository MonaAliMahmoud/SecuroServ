package com.securoserv.ui.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securoserv.data.utils.ErrorModel
import com.securoserv.data.utils.Errors
import com.securoserv.di.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

abstract class BaseViewModel<Repository : BaseRepository>
    (val repository: Repository, private val schedulerProvider: SchedulerProvider) : ViewModel() {

    internal val internalState = MutableLiveData<ViewState>()

    val state: LiveData<ViewState> = internalState

    val compositeDisposable = CompositeDisposable()

    fun <T> subscribe(
        observable: Observable<T>?,
        success: Consumer<T>?,
        error: Consumer<Throwable>?
    ) {
        observable
            ?.compose(schedulerProvider.ioToMainObservableScheduler())
            ?.subscribe(success, error).let { compositeDisposable.add(it!!) }
    }

    fun <T> subscribe(
        observable: Single<T>?,
        success: Consumer<T>?,
        error: Consumer<Throwable>? = Consumer {}
    ) {
        observable
            ?.compose(schedulerProvider.ioToMainSingleScheduler())
            ?.compose { single ->
                composeSingle<T>(single)
            }
            ?.subscribe(success, error).let { compositeDisposable.add(it!!) }
    }

    private fun <T> composeSingle(single: Single<T>): Single<T> {
        return single.doOnError {
            when (it) {
                is Errors.CustomErrors -> {
                    internalState.postValue(ViewState.CustomErrorMsg)
                }
                is Errors.InvalidToken -> {
                    internalState.postValue(ViewState.AuthenticationError)
                }
                is Errors.GeneralError -> {
                    internalState.postValue(
                        ViewState.Error(
                            ErrorModel(
                                code = it.code,
                                apiError = it.errorMessage
                            )
                        )
                    )
                }
                is Errors.NetworkError -> {
                    internalState.postValue(ViewState.Network)
                }
                is
                Errors.ServerError,
                Errors.UnExcepted -> {
                    internalState.postValue(
                        ViewState.Error(ErrorModel(apiError = "An error occurred"))
                    )
                }
                else -> {
                    internalState.postValue(
                        ViewState.Error(ErrorModel(apiError = "An error occurred, please try again"))
                    )
                }
            }

        }
    }

    private fun clearSubscription() {
        Log.i("clear", "started")

        if (compositeDisposable.isDisposed.not()) {
            Log.i("clear", "true")
            internalState.postValue(ViewState.HideLoading())
            compositeDisposable.clear()
        }
    }

    override fun onCleared() {
        clearSubscription()
        super.onCleared()
    }
}