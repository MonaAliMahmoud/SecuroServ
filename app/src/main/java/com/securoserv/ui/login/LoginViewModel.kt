package com.securoserv.ui.login

import com.securoserv.di.rx.SchedulerProvider
import com.securoserv.ui.base.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    loginRepository: LoginRepository,
    schedulerProvider: SchedulerProvider
) : BaseViewModel<LoginRepository>(loginRepository, schedulerProvider)