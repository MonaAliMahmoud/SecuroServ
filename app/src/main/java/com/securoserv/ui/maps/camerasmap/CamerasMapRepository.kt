package com.securoserv.ui.maps.camerasmap

import com.securoserv.data.local.SharedPrefUtils
import com.securoserv.data.model.Cameras
import com.securoserv.data.model.Zones
import com.securoserv.ui.base.BaseRepository
import retrofit2.Retrofit
import javax.inject.Inject

class CamerasMapRepository @Inject constructor(
    val retrofit: Retrofit,
    val sharedPrefUtils: SharedPrefUtils
) : BaseRepository() {

    var cameras = ArrayList<Cameras>()

    fun getZoneCameras(id: String): ArrayList<Cameras> {
        cameras.addAll(
            arrayListOf(
                Cameras("camera 1", "31.21490987771362, 29.94624436625268"),
                Cameras("camera 2", "31.216660739204002, 29.94626845167423"),
                Cameras("camera 3", "31.253004243392006, 30.002256927548537"),
                Cameras("camera 4", "31.28846572763542, 30.015964214048168")
            )
        )
        return cameras
    }
}
