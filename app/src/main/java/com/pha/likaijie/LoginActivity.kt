package com.pha.likaijie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pha.likaijie.bean.LocGroupBean
import com.pha.likaijie.bean.LoginBean
import com.pha.likaijie.common.Session
import com.pha.likaijie.databinding.ActivityLikaijieBinding
import com.pha.likaijie.databinding.ActivityLoginBinding
import com.pha.likaijie.viewmodel.LoginViewModel
import com.pha.likaijie.viewmodel.likaijieViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        //val binding= DataBindingUtil.setContentView<ActivityLikaijieBinding>(this,R.layout.activity_likaijie)
        //val viewModel = ViewModelProviders.of(this).get(MvvmViewModel::class.java)
        val  binding=DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login)

        val viewModel =
            ViewModelProvider.AndroidViewModelFactory(application).create(LoginViewModel::class.java)

        binding.apply {
            vm = viewModel
//            btnConfigDatabase.setOnSingleClickListener {
//                val intent = Intent(context, DatabaseConfigActivity::class.java)
//                startActivityForResult(intent, 1)
//            }
            btn_login.setOnClickListener() {

               if (binding.spLoc.count==0){

                   viewModel.Login()

               } else {

                   Session.locDesc = (binding.spLoc.selectedItem as LocGroupBean).locDesc
                   Session.groupDesc = (binding.spLoc.selectedItem as LocGroupBean).groupDesc
                   Session.locID = (binding.spLoc.selectedItem as LocGroupBean).locID
                   Session.groupID = (binding.spLoc.selectedItem as LocGroupBean).groupID

                   var  intent=Intent(this@LoginActivity,QueryOrdActivity::class.java)
                   startActivity(intent)
               }

            }
        }

         viewModel.apply {
             liveDataRsp.observe(this@LoginActivity, Observer {
                 val result = it
                 it?.let {
                     Session.userID = it.userID
                     Session.userName = it.userName
                     Session.userCode = it.userCode
                     Session.ftpUrl = it.ftpUrl
                     Session.ftpPassword = it.ftpPassword
                     Session.ftpPath = it.ftpPath
                     Session.ftpUsername = it.ftpUsername
                     Session.ftpPort = it.ftpPort
                 }

                 val adapter = ArrayAdapter(
                     this@LoginActivity,
                     R.layout.spinner_select,
                     result?.rows as MutableList<LocGroupBean>
                 )
                 adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                 binding.spLoc.prompt = "请选择科室"
                 binding.spLoc.adapter = adapter

                 })

         }

    }



    }
