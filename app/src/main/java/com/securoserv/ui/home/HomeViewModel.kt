package com.securoserv.ui.home

import com.securoserv.di.rx.SchedulerProvider
import com.securoserv.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    loginRepository: HomeRepository,
    schedulerProvider: SchedulerProvider
) : BaseViewModel<HomeRepository>(loginRepository, schedulerProvider)