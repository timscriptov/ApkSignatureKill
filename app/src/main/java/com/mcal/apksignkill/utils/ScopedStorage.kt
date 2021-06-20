package com.mcal.apksignkill.utils

import android.os.Build
import android.os.Environment
import com.mcal.apksignkill.App
import java.io.File


object ScopedStorage {
    @JvmStatic
    fun getStorageDirectory(): File? {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q && !Environment.isExternalStorageManager()) {
            App.getContext()!!.getExternalFilesDir(Environment.getDataDirectory().absolutePath)
        } else {
            Environment.getExternalStorageDirectory()
        }
    }

    @JvmStatic
    fun getRootDirectory(): File? {
        return Environment.getExternalStorageDirectory()
    }

    @JvmStatic
    fun getWorkPath(): String? {
        return App.getContext()!!.filesDir.absolutePath
    }
}