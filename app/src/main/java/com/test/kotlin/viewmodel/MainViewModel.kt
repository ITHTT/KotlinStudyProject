package com.test.kotlin.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.test.kotlin.network.HttpService
import com.test.kotlin.network.NetworkApi
import com.test.library_base.base.BaseViewModel

/**
 * Created by NullPointerException on 2020/5/7.
 */
class MainViewModel(app:Application) :BaseViewModel(app){
    private var advertData= MutableLiveData<String>()

    fun getAdvert(): MutableLiveData<String>{
        launchGo({
            NetworkApi.getInstance().create(HttpService::class.java).getHomeAdvert()
        }, {
                advertData.value=it.toString()
            },{
            LogUtils.e(it.msg)
        })
        return advertData
    }
}