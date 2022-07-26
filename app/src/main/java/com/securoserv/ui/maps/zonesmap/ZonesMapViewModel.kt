package com.securoserv.ui.maps.zonesmap

import com.securoserv.data.model.Zones
import com.securoserv.di.rx.SchedulerProvider
import com.securoserv.ui.base.BaseViewModel
import javax.inject.Inject

class ZonesMapViewModel @Inject constructor(
    zonesMapRepository: ZonesMapRepository,
    schedulerProvider: SchedulerProvider
) : BaseViewModel<ZonesMapRepository>(zonesMapRepository, schedulerProvider){

    fun getAllZones(id: String): ArrayList<Zones>{
       return repository.getZone(id)
    }
}