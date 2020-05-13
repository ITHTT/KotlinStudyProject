package com.test.kotlin.ui.fragment

import android.os.Bundle
import android.view.View
import com.test.kotlin.R
import com.test.kotlin.databinding.FragmentMainActivityBinding
import com.test.kotlin.viewmodel.MainActivityViewModel
import com.test.library_base.base.BaseFragment

class MainActivityFragment: BaseFragment<MainActivityViewModel, FragmentMainActivityBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_main_activity

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }
}