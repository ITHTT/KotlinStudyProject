package com.test.kotlin.ui.fragment

import android.os.Bundle
import android.view.View
import com.test.kotlin.R
import com.test.kotlin.databinding.FragmentMainSiteBinding
import com.test.kotlin.viewmodel.MainSiteViewModel
import com.test.library_base.base.BaseFragment

class MainSiteFragment :BaseFragment<MainSiteViewModel,FragmentMainSiteBinding>(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_main_site
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }
}