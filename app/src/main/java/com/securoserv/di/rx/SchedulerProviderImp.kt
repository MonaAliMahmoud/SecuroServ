package com.securoserv.di.rx

import io.reactivex.*
import javax.inject.Inject

class SchedulerProviderImp @Inject constructor() : SchedulerProvider {
    override fun <T> ioToMainObservableScheduler(): ObservableTransformer<T, T> =
        ObservableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }

    override fun <T> ioToMainSingleScheduler(): SingleTransformer<T, T> =
        SingleTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }

    override fun ioToMainCompletableScheduler(): CompletableTransformer =
        CompletableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }

    override fun <T> ioToMainFlowableScheduler(): FlowableTransformer<T, T> =
        FlowableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }

    override fun <T> ioToMainMaybeScheduler(): MaybeTransformer<T, T> =
        MaybeTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }
}