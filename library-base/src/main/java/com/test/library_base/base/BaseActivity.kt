package com.test.library_base.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.test.library_base.util.ClassUtil


abstract class BaseActivity<VM:AndroidViewModel,VDB:ViewDataBinding>:AppCompatActivity(){
    var viewModel:VM? = null
    var bindingView:VDB? = null

    var loadService: LoadService<*>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        if(isImmersionBar()) initImmersionBar()
        initLoadService(getLoadView())
        initViewModel();
        initView(intent,savedInstanceState)


    }

    override fun setContentView(layoutResID: Int) {
        bindingView=DataBindingUtil.inflate(LayoutInflater.from(this),layoutResID,null,false)
        bindingView?.lifecycleOwner = this
        setContentView(bindingView?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    open fun initViewModel(){
        var classType=ClassUtil.getViewModel<VM>(this)
        classType?.apply {
//            LogUtils.e("ClassType",this.name)
            viewModel=ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(this)
        }
    }

    abstract fun getLayoutId():Int

    abstract fun initView(intent: Intent?,savedInstanceState: Bundle?)

    open fun getLoadView(): View?{
        return null
    }

    private fun initLoadService(view:View?){
        loadService=view?.let {
            LoadSir.getDefault().register(it) {onReload(view)}
        }
    }

    open fun onReload(view:View?){

    }

    open fun isImmersionBar():Boolean{
        return true
    }

    open fun getTitleBar():View?{
        return null
    }

    protected open fun initImmersionBar() {
        //在BaseActivity里初始化
        immersionBar{
            transparentBar()
            keyboardEnable(true)
            hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
            statusBarDarkFont(true)
            getTitleBar()?.apply {
                LogUtils.d("设置标题栏...")
                titleBar(this)
            }
            init()
        }
    }
}