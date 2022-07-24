package com.securoserv.di.module

import com.securoserv.MainActivity
import com.securoserv.di.builder.MainActivityFragmentBuilderModule
import com.securoserv.ui.login.LoginActivity
import com.securoserv.ui.signup.SignUpActivity
import com.securoserv.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    abstract fun contributeLoginActivity(): LoginActivity

    abstract fun contributeSignUpActivity(): SignUpActivity

    @ContributesAndroidInjector(modules = [MainActivityFragmentBuilderModule::class])
    abstract fun contributeHomeActivity(): MainActivity
}