package com.pha.likaijie

import android.view.View
import com.pha.likaijie.adapter.QueryOrdAdapter
import com.pha.likaijie.base.BaseActivity
import com.pha.likaijie.bean.OrdBean
import com.pha.likaijie.databinding.ActivityQueryOrdBinding
import com.pha.likaijie.viewmodel.QueryOrdViewModel
import kotlinx.android.synthetic.main.activity_query_ord.*


class QueryOrdActivity : BaseActivity<QueryOrdViewModel,ActivityQueryOrdBinding>() {
    override fun getContentViewId(): Int = R.layout.activity_query_ord
    override fun initConfig() {
        super.initConfig()

    }

    override fun initView() {
        super.initView()
        iv_confirm.setOnClickListener {
            initList()
        }
    }

    override fun initData() {
        super.initData()
        initList()
    }
   fun initList(){
       val params: String = mBinding.edtStart.text.toString() + "#" + mBinding.edtOrd.text + "#" + mBinding.edtBarCode.text.toString()

       mViewModel.getData("QueryOrd", params)
           .observeArrayLiveData(OrdBean::class.java) {
               setList(it!!)
           }
    }
    fun setList(list: List<OrdBean>) {
        val adapter = QueryOrdAdapter(list)

        mBinding.elvList.setAdapter(adapter)



    }
}

