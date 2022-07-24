package com.securoserv.di.module

import com.securoserv.ui.base.BaseRepository
import com.securoserv.ui.home.HomeRepository
import com.securoserv.ui.login.LoginRepository
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
}