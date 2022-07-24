package com.securoserv.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.securoserv.data.model.UserData
import javax.inject.Inject

class SharedPrefUtils @Inject constructor(context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)

    private fun putString(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    private fun getString(key: String) = pref.getString(key, "")!!

    private fun putInt(key: String, value: Int) {
        pref.edit().putInt(key, value).apply()
    }

    private fun getInt(key: String) = pref.getInt(key, 0)

    private fun putBoolean(key: String, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }

    private fun getBoolean(key: String) = pref.getBoolean(key, false)

    fun getToken(): String {
        return getString(ACCESS_TOKEN)
    }

    fun saveToken(accessToken: String) {
        putString(ACCESS_TOKEN, accessToken)
    }

    fun getId(): String {
        return getString(USER_ID)
    }

    fun saveId(id: String) {
        putString(USER_ID, id)
    }

    fun setUser(user: UserData) {
        val userString = Gson().toJson(user)
        putString(USER, userString)
        login()
    }

    fun getUser(): UserData? {
        val userString = getString(USER)
        if (userString.isBlank()) {
            return null
        }
        return Gson().fromJson(userString, UserData::class.java)
    }

    fun login() {
        putBoolean(LOGIN, true)
    }

    fun isLogin() = getBoolean(LOGIN)

    fun logout() {
        pref.edit().clear().apply()
    }

    companion object {
        const val PREF_FILE = "securoserv_app"
        const val ACCESS_TOKEN = "accessToken"
        const val USER_ID = "user_id"
        const val LOGIN = "is_login"
        const val USER = "user"
    }
}