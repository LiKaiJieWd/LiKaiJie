package com.pha.likaijie.api


import com.pha.likaijie.bean.LKJ
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
/**
 *Create by LiKaiJie
 *Date:2020/12/5
 */
interface Api {
    @GET(value = "users/{userName}")
    fun  getLKJ(@Path(value = "userName") userName:String):Call<LKJ>
}