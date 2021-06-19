package com.mcal.apksignkill

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.jetbrains.annotations.Nullable

/**
 * @author Тимашков Иван
 * @author https://github.com/TimScriptov
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        private var app: Application? = null
        private var preferences: SharedPreferences? = null

        fun getContext(): Context? {
            if (context == null) {
                context = App()
            }
            return context
        }

        fun getApp(): Application? {
            if (app == null) {
                app = App()
            }
            return app
        }

        @JvmStatic
        @Nullable
        fun getPreferences(): SharedPreferences {
            if (preferences == null) {
                preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext())
            }
            return preferences!!
        }
    }
}