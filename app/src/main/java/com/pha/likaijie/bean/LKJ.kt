package com.pha.likaijie.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
/**
 *Create by LiKaiJie
 *Date:2020/12/5
 */
@Entity(tableName = "lkj")
data class LKJ (
    // PrimaryKey 是主键
    @PrimaryKey @ColumnInfo(name = "id",typeAffinity = ColumnInfo.INTEGER) var id:Int,

    @ColumnInfo (name = "login",typeAffinity = ColumnInfo.TEXT) var login:String,

    @ColumnInfo(name = "name",typeAffinity = ColumnInfo.TEXT) var name: String?,

    @ColumnInfo(name="avatar_url",typeAffinity = ColumnInfo.TEXT) @SerializedName(value = "avatar_url") var avatar:String?,

    @ColumnInfo(name = "blog", typeAffinity = ColumnInfo.TEXT) var blog: String,

    @ColumnInfo(name = "company", typeAffinity = ColumnInfo.TEXT) var company: String?,

    @ColumnInfo(name = "bio", typeAffinity = ColumnInfo.TEXT) var bio: String?,

    @ColumnInfo(name = "location", typeAffinity = ColumnInfo.TEXT) var location: String?,

    @ColumnInfo(name = "htmlUrl", typeAffinity = ColumnInfo.TEXT) @SerializedName("html_url") var htmlUrl: String?



    )