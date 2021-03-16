package com.pha.likaijie.api

import com.pha.likaijie.common.Session
import com.pha.likaijie.network.KtHttpLogInterceptor
import com.pha.likaijie.network.LocalCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

/**
 *Create by LiKaiJie
 *Date:2020/12/5
 */
object RetrofitClient {
    public  var BASE_URL =  "${Session.networkProtocol}://${Session.databaseIP}/"

    var retrofit: Retrofit

    init {
        retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getApi():Api?{

        return retrofit.create(Api::class.java)
    }

    private val mOkClient = OkHttpClient.Builder().callTimeout(10, TimeUnit.SECONDS) //完整请求超时时长，从发起到接收返回数据，默认值0，不限定,
        .connectTimeout(10, TimeUnit.SECONDS) //与服务器建立连接的时长，默认10s
        .readTimeout(10, TimeUnit.SECONDS) //读取服务器返回数据的时长
        .writeTimeout(10, TimeUnit.SECONDS) //向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true) //重连
        .followRedirects(false) //重定向
        .cookieJar(LocalCookieJar()).addNetworkInterceptor(KtHttpLogInterceptor{logLevel(KtHttpLogInterceptor.LogLevel.BODY)})
        .build()

    fun <T> create(serviceClass: Class<T>): T = Retrofit.Builder().baseUrl(BASE_URL).client(mOkClient).addConverterFactory(GsonConverterFactory.create()).build().create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)
}