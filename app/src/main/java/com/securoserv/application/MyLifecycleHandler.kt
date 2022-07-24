package com.securoserv.application

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.util.*

open class MyLifecycleHandler : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        created++
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
    }
}