package com.securoserv.di.module

import androidx.lifecycle.ViewModel
import com.securoserv.di.helper.ViewModelKey
import com.securoserv.ui.home.HomeViewModel
import com.securoserv.ui.login.LoginViewModel
import com.securoserv.ui.maps.camerasmap.CamerasMapViewModel
import com.securoserv.ui.maps.zonesmap.ZonesMapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ZonesMapViewModel::class)
    abstract fun bindMapViewModel(zonesMapViewModel: ZonesMapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CamerasMapViewModel::class)
    abstract fun bindCamerasMapViewModel(camerasMapViewModel: CamerasMapViewModel): ViewModel
}