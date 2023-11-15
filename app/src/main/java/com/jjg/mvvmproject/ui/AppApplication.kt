package com.jjg.mvvmproject.ui

import android.app.Application
import timber.log.Timber

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return "${element.fileName}#${element.methodName}#${element.lineNumber}"
            }
        })
    }
}