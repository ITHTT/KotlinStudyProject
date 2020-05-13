package com.test.kotlin.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.google.gson.JsonObject
import com.test.kotlin.bean.HomeDataEntity
import com.test.kotlin.bean.HomeNewsEntity
import com.test.kotlin.network.HttpService
import com.test.kotlin.network.NetworkApi
import com.test.library_base.base.BaseViewModel
import com.test.library_base.network.BaseResponse

class MainHomeViewModel(app:Application):BaseViewModel(app){
    var data=MutableLiveData<HomeDataEntity>()
    var newsData=MutableLiveData<BaseResponse<HomeNewsEntity>>()

    fun getHomeData():MutableLiveData<HomeDataEntity>{
        launchGo({
            NetworkApi.getInstance().create(HttpService::class.java).getHomeData()
        },{
            data.value=it
        },{

        })
        return data
    }

    fun getLiveNews():MutableLiveData<BaseResponse<HomeNewsEntity>>{
        launchResult({
            NetworkApi.getInstance().create(HttpService::class.java).getLiveNews(pageNumber = 1,pageSize = 4)
        },callback = {
            newsData.value=it
        })
        return newsData
    }
}