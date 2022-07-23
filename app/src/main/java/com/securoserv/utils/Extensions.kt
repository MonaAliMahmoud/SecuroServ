package com.securoserv.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.securoserv.R

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

// activity
fun hideSoftKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        activity.currentFocus!!.windowToken, 0
    )
}

// for fragment
fun View.showKeyborad(context: Context) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}


fun Context.isNetworkActive(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}

fun Context.isNetworkActiveWithMessage(): Boolean {
    val isActive = isNetworkActive()

    if (!isActive)
        Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show()

    return isActive
}