package com.signs.yowal.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.mcal.apkkiller.BuildConfig
import com.mcal.apkkiller.R

/**
 * @author Тимашков Иван
 * @author https://github.com/TimScriptov
 */
object Dialogs {

    /**
     * Диалог с просьбой предоставить дополнительное разрешение на Android 11
     */
    @RequiresApi(Build.VERSION_CODES.R)
    @JvmStatic
    fun showScopedStorageDialog(context: Context) {
        AlertDialog.Builder(context)
                .setTitle(R.string.scoped_storage_title)
                .setMessage(R.string.scoped_storage_msg)
                .setPositiveButton(R.string.settings_title) { p1, p2 ->
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:" + BuildConfig.APPLICATION_ID))
                    context.startActivity(intent)
                }
                .create().show()
    }
}