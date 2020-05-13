package com.test.kotlin.ui.fragment

import android.os.Bundle
import android.view.View
import com.test.kotlin.R
import com.test.kotlin.databinding.FragmentMainVenueBinding
import com.test.kotlin.viewmodel.MainVenueViewModel
import com.test.library_base.base.BaseFragment


class MainVenueFragment:BaseFragment<MainVenueViewModel,FragmentMainVenueBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_main_venue
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }
}