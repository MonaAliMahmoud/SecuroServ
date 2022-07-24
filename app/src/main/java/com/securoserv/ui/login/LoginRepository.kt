package com.securoserv.ui.login

import com.securoserv.data.local.SharedPrefUtils
import com.securoserv.ui.base.BaseRepository
import retrofit2.Retrofit
import javax.inject.Inject

class LoginRepository @Inject constructor(
    val retrofit: Retrofit,
    val sharedPrefUtils: SharedPrefUtils
) : BaseRepository()
