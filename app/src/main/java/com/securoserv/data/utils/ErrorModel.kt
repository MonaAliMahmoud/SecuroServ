package com.securoserv.data.utils

import androidx.annotation.StringRes

data class ErrorModel(val code: Int? = 0, val apiError: String? = null, @StringRes val localError: Int? = null)