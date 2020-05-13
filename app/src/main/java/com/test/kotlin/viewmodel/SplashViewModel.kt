package com.test.kotlin.viewmodel

import android.app.Application
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.test.kotlin.network.HttpService
import com.test.kotlin.network.NetworkApi
import com.test.library_base.base.BaseViewModel


class SplashViewModel(app:Application):BaseViewModel(app) {
    var splashImage=MutableLiveData<String>()

    fun checkUpdate(updateDialog:(url:String,content:String,version:Int,flag:String)->Unit,
                    xieYiDialog:()->Unit,
                    animationSkip: (imgUrl:String?, clickUrl:String?) -> Unit){
        launchGo({
            NetworkApi.getInstance().create(HttpService::class.java).checkAppUpdate()
        },{
            if(it.get("returnCode").asInt==1){
                if(it.get("returnData").asJsonObject.get("pushStatus").asInt==0) //不推送
                    getConfigInfo(xieYiDialog,animationSkip)
                else{
                    //推送更新
                    with(it.get("returnData").asJsonObject){
                        var downloadUrl=get("downloadUrl").asString
                        var content=get("content").asString
                        var flag=get("isForceUpdate").asString
                        var versionCode=get("vercode").asInt
                        if(AppUtils.getAppVersionCode()<versionCode){
                            updateDialog(downloadUrl,content,versionCode,flag)
                        }else{
                            getConfigInfo(xieYiDialog,animationSkip)
                        }
                    }
                }
            }else{
                getConfigInfo(xieYiDialog,animationSkip)
            }
        },{
            error(it)
            getConfigInfo(xieYiDialog,animationSkip)
        })
    }

    private fun getConfigInfo(showDialog:()->Unit,animationSkip: (String?, String?) -> Unit){
        launchGo({
            NetworkApi.getInstance().create(HttpService::class.java).getConfigInfo()
        },{
            if(it.get("open").asBoolean){
                if(System.currentTimeMillis()>it.get("start").asLong
                    &&System.currentTimeMillis()<it.get("end").asLong){
                    LogUtils.e("加载图片...")
                    splashImage.value=it.get("img").asString
                }else{
                    if(SPUtils.getInstance().getString("version") != AppUtils.getAppVersionCode().toString()){
                        //显示协议框
                        showDialog()
                    }else{
                        getSplashImage(animationSkip)
                    }
                }
            }else{
                LogUtils.e("加载图片...")
                splashImage.value=it.get("img").asString
            }
        },{
            getSplashImage(animationSkip)
        })
    }

    fun getSplashImage(animationSkip:(imgUrl:String?,clickUrl:String?)->Unit){
        launchGo({
            LogUtils.i("获取广告图...")
            NetworkApi.getInstance().create(HttpService::class.java).getSplashImage()
        },{
           if(it.get("returnCode").asInt==1){
               Handler().postDelayed({
                   with(it.get("returnData")?.asJsonObject){
                       animationSkip(this?.get("spalshImgUrl")?.asString,this?.get("clickUrl")?.asString)
                   }
               },1500)
           }else{
               animationSkip(null,it.get("returnData")?.asJsonObject?.get("clickUrl")?.asString)
           }
        },{
            animationSkip(null,null)
        })
    }
}