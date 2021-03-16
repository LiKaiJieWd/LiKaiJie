package com.pha.likaijie.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.blankj.utilcode.util.LogUtils
import com.pha.likaijie.MyApplication
import com.pha.likaijie.api.RetrofitClient
import com.pha.likaijie.bean.*
import com.pha.likaijie.common.SingleLiveData
import com.pha.likaijie.dao.AppDatabase
import com.pha.likaijie.dao.LKJDao
import com.pha.likaijie.network.CommonWork
import com.pha.likaijie.network.Service
import com.pha.likaijie.network.serverData
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

/**
 *Create by LiKaiJie
 *Date:2020/12/5
 */
object LKJRepository {

    var LKJDao: LKJDao? = AppDatabase.getDatabase(MyApplication.context)?.lkjDao()

    fun getLKJ(name: String): LiveData<LKJ>? {
        refresh(name)
        var obj = LKJDao?.getLKJByName(name)
        return obj
    }

    fun refresh(name: String) {

        RetrofitClient.getApi()?.getLKJ(name)?.enqueue(object : Callback<LKJ> {
            override fun onFailure(call: Call<LKJ>, t: Throwable) {

            }

            override fun onResponse(call: Call<LKJ>, response: Response<LKJ>) {

                if (response.body() != null) {
                    insertLKJ(response.body()!!)
                }
            }


        })
    }

    fun insertLKJ(user: LKJ) {
        thread {

            LKJDao?.insertLKJ(user)

        }
    }

    private val _dataRsp = SingleLiveData<LoginBean>()
    val dataRsp: LiveData<LoginBean> = _dataRsp
    private val _errorRsp = SingleLiveData<String>()
    val errorRsp: LiveData<String> = _errorRsp

    suspend fun requestData(methodName: String, params: String) {
        RetrofitClient.create(Service::class.java).getApi(methodName = methodName, params = params)
            .serverData().onSuccess {
                //只要不是接口响应成功，
                onBizError { message ->

                    LogUtils.w("登录接口 BizError $message")
                }
                onBizOK<LoginBean> { data, message ->
                    _dataRsp.value = data
                    LogUtils.i("登录接口 BizOK $data")
                }
            }.onFailure {

                LogUtils.e("登录接口 异常 ${it.message}")
            }

    }

    val isLoading = MutableLiveData(false) //标记网络loading状态  LiveData类型的String


    //协程处理数据  但是看不懂啊  异步获取获取 调用这玩意怎么就报错了呢  Dispatchers.IO 在子线程玩
    fun <T> serverAwaitLive(block: suspend LiveDataScope<Result<T>>.() -> Unit) = liveData<Result<T>>(Dispatchers.IO) {

        isLoading.postValue(true)
        block.invoke(this)
        isLoading.postValue(false)
    }

    fun getData(methodName: String?, params: String?) = serverAwaitLive<CommonBean> {
        val result = try {
            // 获取json字符串
            val commonBean = CommonWork.getData(methodName, params)
            Result.success(commonBean)
        }catch (e:Exception){
            Result.failure<Thread>(e)
        }
        emit(result)   //更新数据
    }

    private fun emit(value: Result<Any>) {

    }

}

private fun Any.invoke(liveDataScope: LiveDataScope<Result<CommonBean>>) {

}


