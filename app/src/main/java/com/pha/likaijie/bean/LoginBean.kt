package com.pha.likaijie.bean

import com.blankj.utilcode.util.EncryptUtils

data class LoginBean(
    var userCode: String?,
    var userName: String?,
    var userID: String?,
    var ftpUrl: String?,
    var ftpPort: String?,
    var ftpUsername: String?,
    var ftpPassword: String?,
    var ftpPath: String?,
    var rows: List<LocGroupBean>?
)


