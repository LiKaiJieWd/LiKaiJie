package com.pha.likaijie.ktx

import android.widget.Toast
import com.pha.likaijie.common.BaseApplication

fun String.showToast() {
    Toast.makeText(BaseApplication.context, this, Toast.LENGTH_SHORT).show()
}