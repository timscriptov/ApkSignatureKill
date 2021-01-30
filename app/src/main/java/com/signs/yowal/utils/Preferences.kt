package com.signs.yowal.utils

import com.signs.yowal.App

object Preferences {
    @JvmStatic
    fun isApkPath(): String? {
        return App.getPreferences().getString("apkPath", "")
    }
}