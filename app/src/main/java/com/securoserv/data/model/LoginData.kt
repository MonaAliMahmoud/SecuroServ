package com.securoserv.data.model

import com.google.gson.annotations.SerializedName

data class LoginData (
    @SerializedName("user")
    val user: UserData? = null
)