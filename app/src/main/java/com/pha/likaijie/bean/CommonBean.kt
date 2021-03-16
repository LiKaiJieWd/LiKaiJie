package com.pha.likaijie.bean

import android.text.TextUtils
import com.pha.likaijie.network.DataResult
import com.pha.likaijie.network.succeeded
import com.pha.likaijie.utils.CodeUtils
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

//data class CommonBean<T>(
//    var retVal: String?,
//    var retObject: T?,
//    var retArray: List<T>?,
//    var retError: String?
//)


data class CommonBean(
    var retVal: String?,
    var retObject: String?,
    var retArray: String?,
    var retError: String?
)

/**
 * 这里表示网络请求成功，并得到业务服务器的响应，至于业务成功失败，另一说
 * 将BaseCniaoRsp的对象，转化为需要的对象类型，也就是将body.string转为entity
 * @return 返回需要的类型对象，可能为null，如果json解析失败的话
 */
inline fun <reified T> CommonBean.toEntity(): T? {
    if (retObject == null) {
        com.blankj.utilcode.util.LogUtils.e("Server Response Json Ok, But data=null, $retError")
        return null
    }
    //gson不允许我们将json对象采用String,所以单独处理
    if (T::class.java.isAssignableFrom(kotlin.String::class.java)) {
        return CodeUtils.decodeData(this.retObject) as T
    }
    return kotlin.runCatching {
        com.blankj.utilcode.util.GsonUtils.fromJson(
            CodeUtils.decodeData(this.retObject),
            T::class.java
        )
    }.onFailure { e ->
        e.printStackTrace()
    }.getOrNull()
}


/**
 * 接口成功，但是业务返回code不是1的情况
 */
@OptIn(ExperimentalContracts::class)
inline fun CommonBean.onBizError(crossinline block: (message: String) -> Unit): CommonBean {
    contract {
        callsInPlace(block, kotlin.contracts.InvocationKind.EXACTLY_ONCE)
    }
    if (!TextUtils.isEmpty(retError)) {
        block.invoke(retError ?: "Error Message Null")
    }
    return this
}

/**
 * 接口成功且业务成功code == 1的情况
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T> CommonBean.onBizOK(crossinline action: (data: T?, message: String?) -> Unit): CommonBean {
    contract {
        callsInPlace(action, kotlin.contracts.InvocationKind.EXACTLY_ONCE)
    }
    if (TextUtils.isEmpty(retError)) {
        action.invoke(this.toEntity<T>(), retError)
    }
    return this
}


/**
 * 扩展用于处理网络返回数据结果 网络接口请求成功，但是业务成功与否，另一说
 */
@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onSuccess(
    action: R.() -> Unit
): DataResult<R> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (succeeded) action.invoke((this as DataResult.Success<R>).data)
    return this
}

/**
 * 这是网络请求出现错误的时候，的回调
 */
@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onFailure(
    action: (exception: Throwable) -> Unit
): DataResult<R> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is DataResult.Error) action.invoke(exception)
    return this
}
