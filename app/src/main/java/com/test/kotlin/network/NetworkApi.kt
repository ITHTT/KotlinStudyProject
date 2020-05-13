package com.test.kotlin.network

import com.test.library_base.network.BaseNetwork
import com.test.library_base.network.https.HttpsUtil
import okhttp3.OkHttpClient

/**
 * Created by NullPointerException on 2020/5/7.
 */
class NetworkApi(url:String?) : BaseNetwork(url,true) {

    companion object{
        private var baseUrl:String?=null
        @Volatile private var instance: NetworkApi?=null
        fun getInstance()= instance?: synchronized(this){
            instance?:NetworkApi(baseUrl).also { instance=it }
        }

        fun initNetworkApi(url:String?){
            baseUrl=url
        }
    }

    override fun configOkHttp(builder: OkHttpClient.Builder?) {
        builder?.apply {

        }
    }
}