package com.securoserv.di.component

import android.app.Application
import com.securoserv.application.AppInstance
import com.securoserv.di.module.ActivityModule
import com.securoserv.di.module.AppModule
import com.securoserv.di.module.NetworkModule
import com.securoserv.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        AppModule::class,
        ActivityModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun baseUrl(url: String): Builder

        fun build(): AppComponent
    }

    fun inject(appInstance: AppInstance)
}