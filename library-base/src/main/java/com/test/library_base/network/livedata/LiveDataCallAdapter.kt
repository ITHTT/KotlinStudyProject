package com.test.library_base.network.livedata

import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<R>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<R> {
        return object : LiveData<R>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            LogUtils.d("请求成功："+GsonUtils.toJson(response))
                            postValue(response.body())
                        }
                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            LogUtils.e("请求出现错误...")
                            postValue(null)
                        }
                    })
                }
            }
        }
    }
}