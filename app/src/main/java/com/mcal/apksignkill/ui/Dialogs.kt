package com.mcal.apksignkill.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.mcal.apksignkill.BuildConfig
import com.mcal.apksignkill.R
import com.mcal.apksignkill.utils.DensityUtil


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

    private fun show2(context: Context) {
        val bottomDialog = Dialog(context, R.style.BottomDialog)
        val contentView: View = LayoutInflater.from(context).inflate(R.layout.dialog_content_circle, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.getLayoutParams() as MarginLayoutParams
        params.width = context.getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(context, 16f)
        params.bottomMargin = DensityUtil.dp2px(context, 8f)
        contentView.setLayoutParams(params)
        bottomDialog.setCanceledOnTouchOutside(true)
        bottomDialog.getWindow()?.setGravity(Gravity.BOTTOM)
        bottomDialog.getWindow()?.setWindowAnimations(R.style.BottomDialog_Animation)
        bottomDialog.show()
    }
}