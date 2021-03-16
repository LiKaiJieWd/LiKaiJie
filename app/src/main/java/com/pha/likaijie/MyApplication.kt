package com.pha.likaijie

import android.app.Application
import android.content.Context

/**
 *Create by LiKaiJie
 *Date:2020/12/5
 */
class MyApplication:Application(){

    companion object{
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }

}