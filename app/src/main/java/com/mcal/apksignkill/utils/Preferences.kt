package com.mcal.apksignkill.utils

import com.mcal.apksignkill.App

object Preferences {
    @JvmStatic
    fun isApkPath(): String? {
        return App.getPreferences().getString("apkPath", "")
    }

    @JvmStatic
    fun isCertPassword(): String? {
        return App.getPreferences().getString("certPassword", "")
    }

    @JvmStatic
    fun isSignaturePassword(): String? {
        return App.getPreferences().getString("signaturePassword", "")
    }

    @JvmStatic
    fun isSignatureAlias(): String? {
        return App.getPreferences().getString("signatureAlias", "")
    }

    @JvmStatic
    fun isSignaturePath(): String? {
        return App.getPreferences().getString("signaturePath", "")
    }

    @JvmStatic
    fun isCustomSignature(): Boolean {
        return App.getPreferences().getBoolean("customSignature", false)
    }

    @JvmStatic
    fun getSignApkBoolean(): Boolean {
        return App.getPreferences().getBoolean("signApkBoolean", false)
    }

    @JvmStatic
    fun setSignApkBoolean(flag: Boolean) {
        App.getPreferences().edit().putBoolean("signApkBoolean", flag).apply()
    }

    @JvmStatic
    fun getSignatureV1(): Boolean {
        return App.getPreferences().getBoolean("signatureV1", true)
    }

    @JvmStatic
    fun getSignatureV2(): Boolean {
        return App.getPreferences().getBoolean("signatureV2", true)
    }

    @JvmStatic
    fun getSignatureV3(): Boolean {
        return App.getPreferences().getBoolean("signatureV3", false)
    }

    @JvmStatic
    fun getSignatureV4(): Boolean {
        return App.getPreferences().getBoolean("signatureV4", false)
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