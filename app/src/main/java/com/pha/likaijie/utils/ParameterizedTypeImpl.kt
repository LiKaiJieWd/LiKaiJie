package com.pha.likaijie.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class ParameterizedTypeImpl(val clz: Class<*>) : ParameterizedType {
    override fun getRawType(): Type = List::class.java

    override fun getOwnerType(): Type? = null

    override fun getActualTypeArguments(): Array<Type> = arrayOf(clz)
}
