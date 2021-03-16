package com.pha.likaijie.viewmodel

import androidx.lifecycle.ViewModel
import com.pha.likaijie.repository.LKJRepository

/**
 *Create by LiKaiJie
 *Date:2020/12/5
 */
class likaijieViewModel :ViewModel(){
    val userName="LiKaiJieWd"
    fun getLKJ()=LKJRepository.getLKJ(userName)
    fun refresh()=LKJRepository.refresh(userName)

}