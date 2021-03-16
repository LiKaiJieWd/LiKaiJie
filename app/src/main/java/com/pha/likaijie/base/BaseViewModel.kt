package com.pha.likaijie.base

import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope

import com.pha.likaijie.repository.LKJRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    val TAG = this.javaClass.simpleName
    private val jobs = mutableListOf<Job>()
    val isLoading = LKJRepository.isLoading //标记网络loading状态
    val liveErrorRsp = LKJRepository.errorRsp
    //val liveErrorRsp = LKJRepository.errorRsp

    /**
     * 协程 网络请求
     */
    fun serverAwait(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        isLoading.postValue(true)
        block.invoke(this)
        isLoading.postValue(false)
    }.addTo(jobs)


    fun <T> serverAwaitLive(block: suspend LiveDataScope<T>.() -> Unit) = liveData<T>(Dispatchers.IO) {
        isLoading.postValue(true)
        block.invoke(this)
        isLoading.postValue(false)
    }


    override fun onCleared() {
        jobs.forEach { it.cancel() }
        super.onCleared()
    }
    fun getData(method: String, params: String) = LKJRepository.getData(method, params)

}

/**
 * 扩展函数，用于viewModel中的job 添加到list方便
 */
private fun Job.addTo(list: MutableList<Job>) {
    list.add(this)
}