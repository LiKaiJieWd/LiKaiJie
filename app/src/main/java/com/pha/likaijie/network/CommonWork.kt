package com.pha.likaijie.network

import com.pha.likaijie.common.Session
import com.pha.likaijie.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object CommonWork {
    private val service = RetrofitClient.create(Service::class.java)
    suspend fun getData(methodName: String?, params: String?) = service.getApi(Session.databasePath, Session.httpSessionID, Session.databaseUserName, Session.databasePassword, "1", Session.className, methodName, params).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()

                    val headers = response.headers()
                    val cookies = headers.values("Set-Cookie")
                    if (cookies.isNotEmpty()) {
                        val sessionStr = cookies[0]
                        Session.httpSessionID = sessionStr.substring(0, sessionStr.indexOf(";"))
                    }

                    //val json: String? = Utils.replaceSpecialSymbols(response.body().toString())


                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    private val medicationGuideService = RetrofitMG.create(Service::class.java)
    //suspend fun getMedicationGuideData(searchKey: String?, searchType: String?) = medicationGuideService.getMedicationGuideApi("", searchKey, searchType, "483000", "", "17ef2370e2c54626ab4b2a91bbb4169c").await()


}