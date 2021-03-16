package com.pha.likaijie.base

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import android.transition.Slide
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson

import com.pha.likaijie.utils.CodeUtils

import com.pha.likaijie.widget.CustomProgress
import com.pha.likaijie.bean.CommonBean
import com.pha.likaijie.ktx.showToast
import com.pha.likaijie.utils.JsonUtils
import com.pha.likaijie.utils.JsonUtils.isGoodJson
import com.pha.likaijie.utils.ParameterizedTypeImpl

import java.lang.reflect.ParameterizedType
import java.util.*
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel, VDB : ViewDataBinding> : AppCompatActivity() {

    val TAG = this.javaClass.simpleName

    @LayoutRes
    protected abstract fun getContentViewId(): Int

    protected open fun initConfig() {}

    protected open fun initView() {}

    protected open fun initData() {}

    protected lateinit var mBinding: VDB
    protected lateinit var mViewModel: VM


    lateinit var dialog: CustomProgress
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.e(TAG, "onCreate")
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        //window.enterTransition=Slide()


        /* 初始化ViewModel*/
        mViewModel = ViewModelProvider(this).get(getVmClazz(this))
        mBinding = DataBindingUtil.setContentView(this, getContentViewId())
        mBinding.lifecycleOwner = this

        dialog = CustomProgress.show(this@BaseActivity, "", true, null)!!
        mViewModel.isLoading.observe(this, {
            if (it != null) {
                if (it) {
                    if (!dialog.isShowing) {
                        dialog.show()
                    }
                } else {
                    if (dialog.isShowing) {
                        dialog.dismiss()
                    }
                }
            }
        })
        mViewModel.liveErrorRsp.observe(this, {
            if (dialog.isShowing) {
                dialog.dismiss()
            }
            it.showToast()
        })


        initView() //初始化界面
        initConfig() //初始化变量
        initData() //初始化数据
        activities.add(this)
    }

    private fun <VM> getVmClazz(obj: Any): VM {
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }


    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isInitialized) {
            mBinding.unbind()
        }
        if (activities.contains(this)) {
            activities.remove(this)
        }
        LogUtils.e(TAG, "onDestroy")
    }

    inline fun <reified T : Any> getValue(): T? = getValue(T::class)

    fun <T : Any> getValue(clazz: KClass<T>): T? {
        clazz.constructors.forEach { con ->
            if (con.parameters.size == 0) {
                return con.call()
            }
        }
        return null
    }


    /**
     * 扩展用于liveData便捷写法的函数
     * [block]liveData对象，响应change变化的逻辑块
     */
    protected inline fun <T : Any> LiveData<T>.observeKt(crossinline block: (T?) -> Unit) {
        this.observe(this@BaseActivity, Observer {
            block(it)
        })
    }

    /**
     * 扩展用于liveData便捷写法的函数
     * [block]liveData对象，响应change变化的逻辑块
     */
    protected inline fun <T : Any> LiveData<Result<CommonBean>>.observeObjectLiveData(cls: Class<T>, crossinline block: (T?) -> Unit) {
        this.observe(this@BaseActivity, Observer { it ->
            try {
                if (it.isSuccess) {
                    if (it.getOrNull()?.retVal.toString() == "success") {
                        val commonBean = it.getOrNull()
                        val data = CodeUtils.decodeData(commonBean?.retObject.toString())
                        LogUtils.i(data)
                        val result = if (isGoodJson(data)) {
                            Gson().fromJson(data, cls)
                        } else {
                            data
                        }

                        block(result as T?)
                    } else {
                        it.getOrNull()?.retError?.showToast()
                    }
                }
                if (it.isFailure) {
                    it.getOrThrow().toString().showToast()
                }
            } catch (e: Exception) {
                e.toString().showToast()
            }

        })
    }

    fun <T> String.toBeanList(clazz: Class<*>): List<T> = Gson().fromJson<List<T>>(this, ParameterizedTypeImpl(clazz))

    /**
     * 扩展用于liveData便捷写法的函数
     * [block]liveData对象，响应change变化的逻辑块
     */

    protected inline fun <T> LiveData<Result<CommonBean>>.observeArrayLiveData(cls: Class<T>, crossinline block: (List<T>?) -> Unit) {
        this.observe(this@BaseActivity, Observer { it ->
            try {
                if (it.isSuccess) {
                    if (it.getOrNull()?.retVal.toString() == "success") {
                        val commonBean = it.getOrNull()
                        val data = CodeUtils.decodeData(commonBean?.retObject.toString())
                        LogUtils.i(data)
                        val result = data?.toBeanList<T>(cls)
                        block(result)
                    } else {
                        it.getOrNull()?.retError?.showToast()
                    }
                }
                if (it.isFailure) {
                    it.getOrThrow().toString().showToast()
                }
            } catch (e: Exception) {
                e.toString().showToast()
            }
        })
    }

    protected inline fun View.setOnSingleClickListener(crossinline block: () -> Unit) {
        this.setOnClickListener {
            if (!isWindowLocked()!!) {
                block()
            } else {
                "点击过快！".showToast()
            }
        }
    }

    private var lastClickTime: Long = 0

    open fun isWindowLocked(): Boolean? {
        val current = SystemClock.elapsedRealtime()
        if (current - lastClickTime > 1000) {
            lastClickTime = current

            return false
        }
        return true
    }

    companion object {

        var activities = mutableListOf<Activity>()
    }

    open fun close() {
        for (activity in activities) {
            activity.finish()
        }
        activities.clear()
        System.exit(0)
    }




}
