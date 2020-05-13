package com.test.kotlin.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.test.kotlin.AppCacheInfo
import com.test.kotlin.bean.HomeDataEntity
import com.test.kotlin.bean.HomeNewsEntity
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HttpService {

    @POST(" ")
    suspend fun checkAppUpdate(@Query("apiKey")key:String="api_get_getAppVersionByType",
                               @Query("deviceType")deviceType:String="DIC_DEVICE_TYPE_2",
                               @Query("appType")appType:String="APP_VERSION_TYPE_DIC_1"):JsonObject

    @GET("http://wenhuabuupdate.hanyastar.com:8090/config/CultureConfig.json")
    suspend fun getConfigInfo():JsonObject

    @POST(" ")
    suspend fun getSplashImage(@Query("apiKey")key:String="api_get_appSpalsh",
                               @Query("deviceType")deviceType:String="DIC_DEVICE_TYPE_2"):JsonObject

    @POST(" ")
    suspend fun getHomeAdvert(@Query("apiKey")key:String="api_post_getAdvert",
                    @Query("deviceType") type:String="DIC_DEVICE_TYPE_2"): JsonObject

    @POST(" ")
    suspend fun getHomeData(@Query("apiKey")key:String="api_get_home_recommend",
                            @Query("deviceType")deviceType:String="DIC_DEVICE_TYPE_2",
                            @Query("userId")userId:String=AppCacheInfo.getUserId()):HomeDataEntity

    @POST(" ")
    suspend fun getLiveNews(@Query("apiKey")key:String="api_post_getLiveNews",
                            @Query("deviceType")deviceType: String="DIC_DEVICE_TYPE_2",
                            @Query("pageNumber")pageNumber:Int,
                            @Query("pageSize")pageSize:Int):HomeNewsEntity
}