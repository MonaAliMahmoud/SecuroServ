package com.securoserv.data.model

import com.google.gson.annotations.SerializedName

data class Cameras(
    @SerializedName("_id")
    val cameraId: String? = "",
    @SerializedName("zoneName")
    val cameraName: String? = ""
)