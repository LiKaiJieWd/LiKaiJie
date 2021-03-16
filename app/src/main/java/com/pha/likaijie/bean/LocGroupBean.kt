package com.pha.likaijie.bean

data class LocGroupBean(
    var groupDesc: String,
    var groupID: String,
    var locDesc: String,
    var locID: String

) {
    override fun toString(): String {
        return "$locDesc - $groupDesc"
    }
}
