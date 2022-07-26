package com.securoserv.data.model

import com.google.gson.annotations.SerializedName

data class Zones(
    @SerializedName("_id")
    val zoneId: String? = "",
    @SerializedName("zoneName")
    val zoneName: String? = "",
    @SerializedName("numberOfCameras")
    val numberOfCameras: String? = ""
)