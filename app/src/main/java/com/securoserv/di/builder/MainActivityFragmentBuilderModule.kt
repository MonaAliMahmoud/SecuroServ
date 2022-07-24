package com.securoserv.di.builder

import com.google.android.gms.maps.MapFragment
import com.securoserv.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}