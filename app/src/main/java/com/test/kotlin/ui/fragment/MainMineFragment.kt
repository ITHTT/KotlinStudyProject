package com.test.kotlin.ui.fragment

import android.os.Bundle
import android.view.View
import com.test.kotlin.R
import com.test.kotlin.databinding.FragmentMainMineBinding
import com.test.kotlin.viewmodel.MainMineViewModel
import com.test.library_base.base.BaseFragment

class MainMineFragment:BaseFragment<MainMineViewModel,FragmentMainMineBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_main_mine
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }
}