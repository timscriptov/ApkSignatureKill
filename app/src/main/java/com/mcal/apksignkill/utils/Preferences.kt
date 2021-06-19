package com.mcal.apksignkill.utils

import com.mcal.apksignkill.App

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

    @JvmStatic
    var superSignatureKill: Boolean
        get() = App.getPreferences().getBoolean("superSignatureKill", false)
        set(value) {
            App.getPreferences().edit().putBoolean("superSignatureKill", value).apply()
        }
}