package com.signs.yowal.utils

import com.signs.yowal.App

object Preferences {
    @JvmStatic
    fun isApkPath(): String? {
        return App.getPreferences().getString("apkPath", "")
    }

    @JvmStatic
    var binMtSignatureKill: Boolean
        get() = App.getPreferences().getBoolean("binMtSignatureKill", false)
        set(value) {
            App.getPreferences().edit().putBoolean("binMtSignatureKill", value).apply()
        }
}