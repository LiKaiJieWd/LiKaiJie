package com.pha.likaijie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider



import com.pha.likaijie.databinding.ActivityLikaijieBinding
import com.pha.likaijie.viewmodel.likaijieViewModel

class likaijieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_likaijie)
        val binding=DataBindingUtil.setContentView<ActivityLikaijieBinding>(this,R.layout.activity_likaijie)
        //val viewModel = ViewModelProviders.of(this).get(MvvmViewModel::class.java)

        val viewModel =ViewModelProvider.AndroidViewModelFactory(application).create(likaijieViewModel::class.java)

        viewModel.getLKJ()?.observe(this, Observer {
            if (it!=null){
                binding.lkj=it
            }
        })

        binding.srlSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            binding.srlSwipeRefreshLayout.isRefreshing=false

        }

    }


}



