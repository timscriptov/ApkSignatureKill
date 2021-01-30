package com.signs.yowal.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author Тимашков Иван
 * @author https://github.com/TimScriptov
 */

class ExceptionActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val mLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT).apply {
            gravity = 17
        }
        val linearLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = mLayoutParams
        }
        val sv = ScrollView(this)
        linearLayout.addView(sv)
        val error = AppCompatTextView(this)
        sv.addView(error)
        setContentView(linearLayout)
        error.text = intent.getStringExtra("error")
    }
}