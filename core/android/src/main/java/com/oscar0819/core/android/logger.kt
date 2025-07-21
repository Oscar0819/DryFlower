package com.oscar0819.core.android

import android.util.Log

fun logger(message: Any) {
    if (BuildConfig.DEBUG) {
        Log.e("Oscar0819 LOG", "${getStackTrace()} \n $message")
    }
}

private fun getStackTrace(): String? {
    var logInfo = "Log Info"
    try {
        val elements = Throwable().stackTrace
        val className = elements[2].className

        logInfo = "$className"
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return logInfo
}