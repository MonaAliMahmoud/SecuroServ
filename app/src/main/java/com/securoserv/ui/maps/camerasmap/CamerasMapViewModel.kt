package com.securoserv.ui.maps.camerasmap

import com.securoserv.data.model.Cameras
import com.securoserv.di.rx.SchedulerProvider
import com.securoserv.ui.base.BaseViewModel
import javax.inject.Inject

class CamerasMapViewModel @Inject constructor(
    camerasMapRepository: CamerasMapRepository,
    schedulerProvider: SchedulerProvider
) : BaseViewModel<CamerasMapRepository>(camerasMapRepository, schedulerProvider){

    fun getZoneCameras(id: String): ArrayList<Cameras>{
        return repository.getZoneCameras(id)
    }
}