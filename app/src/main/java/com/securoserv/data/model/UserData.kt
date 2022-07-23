package com.securoserv.data.model

import com.google.gson.annotations.SerializedName

data class UserData (
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("password")
    val password: String? = "",
    @SerializedName("name")
    val name: String? = ""
)