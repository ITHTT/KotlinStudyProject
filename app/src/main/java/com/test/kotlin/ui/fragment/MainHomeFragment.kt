package com.test.kotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.test.kotlin.R
import com.test.kotlin.bean.HomeBanner
import com.test.kotlin.bean.HomeRmtj
import com.test.kotlin.databinding.FragmentMainHomeBinding
import com.test.kotlin.ui.adapter.MainHomeAdapter
import com.test.kotlin.viewmodel.MainHomeViewModel
import com.test.library_base.base.BaseFragment


class MainHomeFragment :BaseFragment<MainHomeViewModel,FragmentMainHomeBinding>(){
    var bannerAdapter:MainHomeAdapter?=null
    var menuAdapter:MainHomeAdapter?=null
    var recommendAdapter:MainHomeAdapter?=null

    //精彩资讯
    var newsTitleAdapter:MainHomeAdapter?=null
    var newsAdapter:MainHomeAdapter?=null
    //专题
    var specialTitleAdapter:MainHomeAdapter?=null
    var specialAdapter:MainHomeAdapter?=null
    //共享直播
    var liveTitleAdapter:MainHomeAdapter?=null
    var liveAdapter:MainHomeAdapter?=null

    override fun getLayoutId(): Int = R.layout.fragment_main_home

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initRefreshRecyclerView()
        loadData()
    }

    private fun initRefreshRecyclerView(){
        viewBinding?.let {
            it.refreshRecyclerView.setEnableLoadMore(false)
            it.refreshRecyclerView.setOnRefreshListener {

            }

            it.refreshRecyclerView.recyclerView?.setBackgroundResource(R.color.colorWhite)

            var layoutManager:VirtualLayoutManager= VirtualLayoutManager(it.root.context)
            it.refreshRecyclerView.recyclerView?.layoutManager=layoutManager

            var delegateAdapter=DelegateAdapter(layoutManager,true)

            var adapters= arrayListOf<DelegateAdapter.Adapter<*>>()

            bannerAdapter= MainHomeAdapter(0x0001,1,LinearLayoutHelper())
            adapters.add(bannerAdapter!!)

            menuAdapter= MainHomeAdapter(0x0002,1,LinearLayoutHelper())
            adapters.add(menuAdapter!!)

            var titleAdapter=MainHomeAdapter(0x0003,1,LinearLayoutHelper())
            adapters.add(titleAdapter)

            recommendAdapter= MainHomeAdapter(0x0004,1,LinearLayoutHelper())
            adapters.add(recommendAdapter!!)

            newsTitleAdapter= MainHomeAdapter(0x0005,0,LinearLayoutHelper())
            adapters.add(newsTitleAdapter!!)



            delegateAdapter.addAdapters(adapters)
            it.refreshRecyclerView.recyclerView?.adapter=delegateAdapter
        }

    }

    private fun loadData(){
        viewModel?.apply {
            getHomeData().observe(this@MainHomeFragment, Observer {
                bannerAdapter?.bannerData= it.returnData?.homeBanner as MutableList<HomeBanner>
                bannerAdapter?.notifyItemChanged(0)

                recommendAdapter?.recommendData= it.returnData.homeRmtj as MutableList<HomeRmtj>
                recommendAdapter?.notifyItemChanged(0)

            })

            getLiveNews().observe(this@MainHomeFragment, Observer {

            })
        }
    }


}