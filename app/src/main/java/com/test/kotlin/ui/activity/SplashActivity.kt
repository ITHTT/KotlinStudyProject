package com.test.kotlin.ui.activity

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.test.kotlin.AppCacheInfo
import com.test.kotlin.R
import com.test.kotlin.databinding.ActivitySplashBinding
import com.test.kotlin.viewmodel.SplashViewModel
import com.test.library_base.base.BaseActivity


class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {
    private var alphaAnimation: ObjectAnimator? = null

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initView(intent: Intent?, savedInstanceState: Bundle?) {
        checkPermission()
    }

    override fun isImmersionBar(): Boolean {
        return false
    }

    private fun checkPermission(){
        val neededPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
        )
        PermissionUtils.permission(*neededPermissions!!)
            .callback(object:PermissionUtils.FullCallback{
                override fun onGranted(permissionsGranted: MutableList<String>?) {
                    permissionsGranted?.forEach {
                        println("打开的权限:$it")
                    }

                    if(permissionsGranted?.any { it==Manifest.permission.ACCESS_FINE_LOCATION||it==Manifest.permission.ACCESS_COARSE_LOCATION }!!){
                        LogUtils.i("开始定位服务...")
                    }

                    if(permissionsGranted?.any{it==Manifest.permission.READ_EXTERNAL_STORAGE||it==Manifest.permission.WRITE_EXTERNAL_STORAGE}){
                        LogUtils.i("检查更新...")
                        viewModel?.apply {
                            splashImage.observe(this@SplashActivity, Observer{
                                it?.apply {
                                    LogUtils.i("加载图片...")
                                    bindingView?.ivSplash?.let { it1 ->
                                        Glide.with(this@SplashActivity)
                                            .load(this)
                                            .placeholder(R.mipmap.ic_splash)
                                            .error(R.mipmap.ic_splash)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(it1)
                                    }
                                }
                            })
                            checkUpdate(
                                {url, content, version, flag -> updateDialog(url,content,version,flag) },
                                {xieYiDialog()},
                                {imgUrl, clickUrl -> animationSkip(imgUrl,clickUrl) })
                        }
                    }
                }

                override fun onDenied(
                    permissionsDeniedForever: MutableList<String>?,
                    permissionsDenied: MutableList<String>?
                ) {
                    permissionsDenied?.forEach {
                        println("禁止的权限:$it")
                    }
                }

            }).request()
    }

    private fun updateDialog(url:String,content:String,version:Int,flag:String){

    }

    private fun xieYiDialog(){
        LogUtils.e("显示协议框...")
        val dialog=Dialog(this,R.style.DialogTheme)
        dialog.setContentView(R.layout.dialog_user_agreement)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.findViewById<View?>(R.id.tv_agree)?.setOnClickListener {
            SPUtils.getInstance().put("version", AppUtils.getAppVersionCode().toString())
            viewModel?.getSplashImage { imgUrl, clickUrl -> animationSkip(imgUrl,clickUrl) }
            dialog.cancel()
        }
        dialog.findViewById<View?>(R.id.tv_disagree)?.setOnClickListener {
            dialog.cancel()
            finish()
        }
        dialog.findViewById<View?>(R.id.tv_register_agreement)?.setOnClickListener {

        }
        var display=windowManager.defaultDisplay
        var lp=dialog.window.attributes
        lp.gravity=Gravity.CENTER
        lp.height= (display.height*0.70).toInt()
        lp.width= (display.width*0.9).toInt()
        dialog.window.attributes=lp
        dialog.show()
    }

    private fun animationSkip(imgUrl:String?,clickUrl:String?){
        var hasFinish=false
        var hasAD=false
        if(!TextUtils.isEmpty(imgUrl)){
            if(imgUrl!!.endsWith(".gif")){
                hasAD=true
            }
            AppCacheInfo.setSplashImage(imgUrl)
            var url=AppCacheInfo.getSplashPath()
            if(hasAD&&!TextUtils.isEmpty(url)){
                bindingView?.ivSplash?.let {
                    Glide.with(this@SplashActivity)
                        .load(url)
                        .listener(object:RequestListener<Drawable?>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable?>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable?>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                        })
                        .error(R.mipmap.ic_splash)
                        .placeholder(R.mipmap.ic_splash)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(it)
                }
            }else{
                bindingView?.ivSplash?.let {
                    Glide.with(this@SplashActivity)
                        .load(imgUrl)
                        .listener(object:RequestListener<Drawable?>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable?>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable?>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                alphaAnimation =
                                    ObjectAnimator.ofFloat(it, "alpha", 1.0f, 1.0f)
                                alphaAnimation?.duration = if (hasAD) 4000 else 3000.toLong()
                                alphaAnimation?.start()
                                alphaAnimation?.addListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator) {
                                        if(TextUtils.isEmpty(clickUrl)){
                                            hasFinish=true
                                            startActivity(
                                                Intent(
                                                    this@SplashActivity,
                                                    MainActivity::class.java
                                                )
                                            )
                                            finish()
                                        }
                                    }
                                })
                                return false
                            }
                        })
                        .error(R.mipmap.ic_splash)
                        .placeholder(R.mipmap.ic_splash)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(it)
                }
            }

            clickUrl?.apply {
                bindingView?.ivSplash?.let{
                    it.setOnClickListener {
                        finish()
                    }
                }
            }
        }else{
            AppCacheInfo.setSplashImage("")
            AppCacheInfo.setSplashPath("")
        }

        if(!hasFinish){
            alphaAnimation = ObjectAnimator.ofFloat(bindingView?.ivSplash, "alpha", 1.0f, 1.0f)
            alphaAnimation?.duration = if (hasAD) 5000 else 3000.toLong()
            alphaAnimation?.start()
            alphaAnimation?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    if (TextUtils.isEmpty(clickUrl)) {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        alphaAnimation?.apply {
            cancel()
        }
    }
}