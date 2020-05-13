package com.test.kotlin

import com.blankj.utilcode.util.ProcessUtils
import com.test.kotlin.network.HttpUrls
import com.test.kotlin.network.NetworkApi
import com.test.library_base.base.BaseApplication

/**
 * Created by NullPointerException on 2020/5/7.
 */
class App :BaseApplication(){

    override fun onCreate() {
        super.onCreate()
        if(ProcessUtils.isMainProcess()){
            NetworkApi.initNetworkApi(HttpUrls.TEST_BASE_URL)
        }
    }
}