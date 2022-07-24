package com.securoserv.di.module

import android.app.Application
import android.content.Context
import com.securoserv.data.local.SharedPrefUtils
import com.securoserv.di.rx.SchedulerProvider
import com.securoserv.di.rx.SchedulerProviderImp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, RepositoryModule::class])
class AppModule {

    @Provides
    fun provideContext(application: Application) = application.baseContext

    @Provides
    @Singleton
    internal fun provideSchedulerProvider(schedulerProviderImp: SchedulerProviderImp): SchedulerProvider =
        schedulerProviderImp

    @Provides
    fun provideSharedPref(context: Context) = SharedPrefUtils(context)
}
