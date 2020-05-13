package com.test.library_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.test.library_base.util.ClassUtil

abstract class BaseFragment<VM:AndroidViewModel,VDB:ViewDataBinding> :Fragment(){
    var viewModel:VM?=null
    var viewBinding:VDB?=null

    var loadService: LoadService<*>?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        initLoadService(getLoadView())
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView(view,savedInstanceState)
    }

    private fun initViewModel(){
        var classType= ClassUtil.getViewModel<VM>(this)
        if(classType!=null){
            var a=activity;
            if(a!=null) {
                viewModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(a.application)
                        .create(classType)
            }
        }
    }

    abstract fun getLayoutId():Int

    abstract fun initView(view:View,savedInstanceState: Bundle?)

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
}