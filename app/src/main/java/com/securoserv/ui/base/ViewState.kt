package com.securoserv.ui.base

import com.securoserv.data.utils.ErrorModel

abstract class ViewState {
    data class Loading(val isPaging: Boolean? = false) : ViewState()
    data class HideLoading(val isPaging: Boolean? = false) : ViewState()
    object Network : ViewState()
    object CustomErrorMsg: ViewState()
    object AuthenticationError : ViewState()
    data class Error(val error: ErrorModel?) : ViewState()
    abstract class Loaded<out T>(val result: T) : ViewState()
    abstract class LoadedWithoutData(): ViewState()
    object ClearState : ViewState()
}