package com.securoserv.di.module

import com.securoserv.ui.base.BaseRepository
import com.securoserv.ui.home.HomeRepository
import com.securoserv.ui.login.LoginRepository
import com.securoserv.ui.maps.camerasmap.CamerasMapRepository
import com.securoserv.ui.maps.zonesmap.ZonesMapRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLoginRepository(loginRepository: LoginRepository): BaseRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepository: HomeRepository): BaseRepository

    @Binds
    @Singleton
    abstract fun bindMapRepository(zonesMapRepository: ZonesMapRepository): BaseRepository

    @Binds
    @Singleton
    abstract fun bindCamerasMapRepository(camerasMapRepository: CamerasMapRepository): BaseRepository
}