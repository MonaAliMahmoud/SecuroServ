package com.securoserv.application

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.securoserv.di.helper.Injectable
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import java.util.*

open class MyLifecycleHandler : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        created++
        handleActivity(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        created--
    }

    override fun onActivityResumed(activity: Activity) {
        ++resumed
    }

    override fun onActivityPaused(activity: Activity) {
        ++paused
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityStarted(activity: Activity) {
        ++started

        activityName = activity.componentName.className
    }

    override fun onActivityStopped(activity: Activity) {
        ++stopped
    }


    private fun handleActivity(activity: Activity) {
        if (activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {

                    override fun onFragmentAttached(
                        fm: FragmentManager,
                        f: Fragment,
                        context: Context
                    ) {
                        if (f is Injectable) {
                            AndroidSupportInjection.inject(f)
                        }

                        super.onFragmentAttached(fm, f, context)
                    }

                }, true
            )
        }
    }

    companion object {

        private var resumed: Int = 0
        private var paused: Int = 0
        private var started: Int = 0
        private var stopped: Int = 0
        private var created: Int = 0
        private val destroyed: Int = 0

        var activityName: String? = null
            private set

        // And these two public static functions
        val isApplicationVisible: Boolean
            get() = started > stopped

        val isApplicationInForeground: Boolean
            get() = resumed > paused

        val isItTheOnlyActivityOpened: Boolean
            get() = created == 1

        fun ActivityIsRunning(context: Context): Boolean {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val serviceInfoList =
                Objects.requireNonNull(activityManager).getRunningServices(Integer.MAX_VALUE)
            val isServiceFound = false
            for (runningServiceInfo in serviceInfoList) {
                if (context.packageName.equals(
                        runningServiceInfo.service.packageName,
                        ignoreCase = true
                    )
                ) {
                    return true
                }
            }
            return false
        }

        fun isLastOne() = created == 1

    }
}