package com.pha.likaijie.viewmodel


import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pha.likaijie.repository.LKJRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    //账号，密码 的observable 对象 ?  什么是observable 对象
    val obUsername = ObservableField<String>()
    val obPassword = ObservableField<String>()
    val obChecked = ObservableField<Boolean>()
    /**
     * 扩展函数，用于viewModel中的job 添加到list方便
     */
    private fun Job.addTo(list: MutableList<Job>) {
        list.add(this)
    }
    /**
     * 协程 网络请求
     */

    private val jobs = mutableListOf<Job>()

    fun serverAwait(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {

        block.invoke(this)


    }.addTo(jobs)


    val liveDataRsp = LKJRepository.dataRsp


     fun Login(){
        val params=obUsername.get() + "#" + obPassword.get()
         serverAwait {
             LKJRepository.requestData("Login", params)
         }

    }

}