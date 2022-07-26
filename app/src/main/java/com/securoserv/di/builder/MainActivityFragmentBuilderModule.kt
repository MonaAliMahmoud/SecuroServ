package com.securoserv.di.builder

import com.securoserv.ui.home.HomeFragment
import com.securoserv.ui.maps.camerasmap.CamerasMapFragment
import com.securoserv.ui.maps.zonesmap.ZonesMapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeZonesMapFragment(): ZonesMapFragment

    @ContributesAndroidInjector
    abstract fun contributeCamerasMapFragment(): CamerasMapFragment
}