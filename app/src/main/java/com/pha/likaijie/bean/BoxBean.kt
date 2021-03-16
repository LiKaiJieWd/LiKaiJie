package com.pha.likaijie.bean

data class BoxBean(
    val boxNo: String?,
    val count: String?,
    val createUser: String?,
    val dateTime: String?,
    val fromLocDesc: String?,
    val logName: String?,
    val remark: String?,
    val toLocDesc: String?,
    val userName: String?,
    var rows: List<PrescMainBean>?
)