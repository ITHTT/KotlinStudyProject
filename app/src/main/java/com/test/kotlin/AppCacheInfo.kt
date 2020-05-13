package com.test.kotlin

import com.blankj.utilcode.util.SPUtils

object AppCacheInfo {

    fun setSplashImage(url:String){
        SPUtils.getInstance().put("splashurl",url)
    }

    fun getSplashImage():String{
        return SPUtils.getInstance().getString("splashurl")
    }

    fun setSplashPath(path:String){
        SPUtils.getInstance().put("splashfile",path)
    }

    fun getSplashPath():String{
        return SPUtils.getInstance().getString("splashfile")
    }

    fun setUserId(id:String){
        SPUtils.getInstance().put("userId",id)
    }

    fun getUserId():String{
        return SPUtils.getInstance().getString("userId","0")
    }
}