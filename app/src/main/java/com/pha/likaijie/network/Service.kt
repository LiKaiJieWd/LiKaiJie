package com.pha.likaijie.network




import com.pha.likaijie.bean.CommonBean
import com.pha.likaijie.common.Session
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @Headers("charset:utf-8")
    @GET("{path}/web/csp/pha.mob.broker.csp")
    fun getApi(@Path("path") path: String = Session.databasePath, @Header("cookie") cookie: String? = Session.httpSessionID, @Query("CacheUserName") CacheUserName: String = Session.databaseUserName, @Query("CachePassword") CachePassword: String = Session.databasePassword, @Query("CacheNoRedirect") CacheNoRedirect: String = "1", @Query("ClassName") className: String? = Session.className, @Query("MethodName") methodName: String?, @Query("params") params: String?): Call<CommonBean>


}