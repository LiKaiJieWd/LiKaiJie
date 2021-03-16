package com.pha.likaijie.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pha.likaijie.bean.LKJ

/**
 *Create by LiKaiJie
 *Date:2020/12/5
 */
@Dao
interface LKJDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)  //制定冲突策略 冲突了就替换掉
    fun insertLKJ(user: LKJ)

    @Delete
    fun deleteLKJ(user: LKJ)

    @Query(value = "select * from lkj where login=:name")
    fun getLKJByName(name:String):LiveData<LKJ>


}