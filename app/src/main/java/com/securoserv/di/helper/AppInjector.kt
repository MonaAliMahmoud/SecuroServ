package com.securoserv.di.helper

import com.securoserv.application.AppInstance
import com.securoserv.application.MyLifecycleHandler
import com.securoserv.di.component.AppComponent
import com.securoserv.di.component.DaggerAppComponent

object AppInjector {

    lateinit var appComponent: AppComponent

    fun init(appInstance: AppInstance) {
        appComponent = DaggerAppComponent.builder()
            .application(appInstance)
            .baseUrl("")
            .build()

        appComponent.inject(appInstance)

        appInstance.registerActivityLifecycleCallbacks(MyLifecycleHandler())
    }
}
