package com.pha.likaijie.utils

import android.text.TextUtils
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException


object JsonUtils {

    fun isBadJson(json: String?): Boolean {
        return !isGoodJson(json)
    }

    fun isGoodJson(json: String?): Boolean {
        return if (TextUtils.isEmpty(json)) {
            false
        } else try {
            JsonParser().parse(json)
            true
        } catch (e: JsonSyntaxException) {
            false
        } catch (e: JsonParseException) {
            false
        }
        return false
    }
}