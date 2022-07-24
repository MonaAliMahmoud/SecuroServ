package com.securoserv.data.utils

sealed class Errors : RuntimeException() {
    object NetworkError : Errors()
    data class Authentication(val errorMessage: String) : Errors()
    data class InvalidToken(val errorMessage: String) : Errors()
    data class GeneralError(val errorMessage: String, val code: Int) : Errors()
    data class CustomErrors(val errorCode: Int) : Errors()
    object ServerError : Errors()
    object UnExcepted : Errors()
}