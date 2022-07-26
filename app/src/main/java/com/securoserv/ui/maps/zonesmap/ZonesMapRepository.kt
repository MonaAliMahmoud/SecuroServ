package com.securoserv.ui.maps.zonesmap

import com.securoserv.data.local.SharedPrefUtils
import com.securoserv.data.model.Cameras
import com.securoserv.data.model.Zones
import com.securoserv.ui.base.BaseRepository
import retrofit2.Retrofit
import javax.inject.Inject

class ZonesMapRepository @Inject constructor(
    val retrofit: Retrofit,
    val sharedPrefUtils: SharedPrefUtils
) : BaseRepository() {

    var zones = ArrayList<Zones>()

    fun getZone(id: String): ArrayList<Zones> {
        zones.addAll(
            arrayListOf(
                Zones("zone 1", "31.1984,29.8953", "2"),
                Zones("zone 2", "31.2655074,29.998219", "4"),
                Zones("zone 3", "31.240839909522894, 29.963272759281338", "6"),
                Zones("zone 4", "31.28846572763542,30.015964214048168", "8")
            )
        )

        return zones
    }
}
