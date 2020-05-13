package com.test.kotlin.ui.activity


import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.test.kotlin.R
import com.test.kotlin.databinding.ActivityMainBinding
import com.test.kotlin.ui.fragment.*
import com.test.kotlin.viewmodel.MainViewModel
import com.test.library_base.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    var fragments= arrayOfNulls<Fragment>(5)
    var tabList= arrayListOf<CustomTabEntity>()

    var currentIndex=0

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(intent: Intent?, savedInstanceState: Bundle?) {
        initFragments(savedInstanceState)
        initTabLayout()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("current_tab",currentIndex)
    }

    private fun initFragments(savedInstanceState: Bundle?){
        savedInstanceState?.apply {
            fragments[0]=supportFragmentManager.findFragmentByTag(MainHomeFragment::javaClass.name)
            fragments[1]=supportFragmentManager.findFragmentByTag(MainActivityFragment::javaClass.name)
            fragments[2]=supportFragmentManager.findFragmentByTag(MainVenueFragment::javaClass.name)
            fragments[3]=supportFragmentManager.findFragmentByTag(MainSiteFragment::javaClass.name)
            fragments[4]=supportFragmentManager.findFragmentByTag(MainMineFragment::javaClass.name)
            currentIndex=savedInstanceState.getInt("current_tab",0)
        }
        if(fragments[0]==null) fragments[0]=MainHomeFragment()
        if(fragments[1]==null) fragments[1]=MainActivityFragment()
        if(fragments[2]==null) fragments[2]=MainVenueFragment()
        if(fragments[3]==null) fragments[3]=MainSiteFragment()
        if(fragments[4]==null) fragments[4]=MainMineFragment()

    }

    private fun initTabLayout(){
        tabList.add(TabEntity(R.mipmap.tab_home_normal, R.mipmap.tab_home_hover, "首页"))
        tabList.add(TabEntity(R.mipmap.tab_activity_normal, R.mipmap.tab_activity_hover, "活动"))
        tabList.add(TabEntity(R.mipmap.tab_venue_normal, R.mipmap.tab_venue_hover, "场馆"))
        tabList.add(TabEntity(R.mipmap.tab_area_normal, R.mipmap.tab_area_hover, "全国"))
        tabList.add(TabEntity(R.mipmap.tab_my_normal, R.mipmap.tab_my_hover, "我的"))

        bindingView?.apply {
            LogUtils.i("初始化tabLayout...")

            tabLayout.setOnTabSelectListener(object :OnTabSelectListener{
                override fun onTabSelect(position: Int) {

                    startShakeByPropertyAnim(tabLayout.getTitleView(position).parent as View, 1.0f, 1.3f, 2.0f, 300)
                    switchFragment(position)
                }

                override fun onTabReselect(position: Int) {
                    LogUtils.i("reselectPosition:$position")
                }

            })
            tabLayout.setTabData(tabList)

            tabLayout.currentTab=currentIndex
            switchFragment(currentIndex)
        }

    }

    private fun switchFragment(index:Int){
        var transaction= this.supportFragmentManager.beginTransaction()
        fragments[currentIndex]?.apply {
            //隐藏先前显示的
            if(index!=currentIndex)
               transaction.hide(this)
        }

        fragments[index]?.apply {
            if(this.isAdded)
                transaction.show(this)
            else
                transaction.add(R.id.layout_main_container,this,this::javaClass.name)
        }
        currentIndex=index
        transaction.commitAllowingStateLoss()

    }

    private fun startShakeByPropertyAnim(
        view: View?,
        scaleSmall: Float,
        scaleLarge: Float,
        shakeDegrees: Float,
        duration: Long
    ) {
        if (view == null) {
            return
        }
        //TODO 验证参数的有效性

        //先变小后变大
        val scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(
            View.SCALE_X,
            Keyframe.ofFloat(0f, 1.0f),
            Keyframe.ofFloat(0.25f, scaleSmall),
            Keyframe.ofFloat(0.5f, scaleLarge),
            Keyframe.ofFloat(0.75f, scaleLarge),
            Keyframe.ofFloat(1.0f, 1.0f)
        )
        val scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(
            View.SCALE_Y,
            Keyframe.ofFloat(0f, 1.0f),
            Keyframe.ofFloat(0.25f, scaleSmall),
            Keyframe.ofFloat(0.5f, scaleLarge),
            Keyframe.ofFloat(0.75f, scaleLarge),
            Keyframe.ofFloat(1.0f, 1.0f)
        )

        //先往左再往右
        val rotateValuesHolder = PropertyValuesHolder.ofKeyframe(
            View.ROTATION,
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.1f, -shakeDegrees),
            Keyframe.ofFloat(0.2f, shakeDegrees),
            Keyframe.ofFloat(0.3f, -shakeDegrees),
            Keyframe.ofFloat(0.4f, shakeDegrees),
            Keyframe.ofFloat(0.5f, -shakeDegrees),
            Keyframe.ofFloat(0.6f, shakeDegrees),
            Keyframe.ofFloat(0.7f, -shakeDegrees),
            Keyframe.ofFloat(0.8f, shakeDegrees),
            Keyframe.ofFloat(0.9f, -shakeDegrees),
            Keyframe.ofFloat(1.0f, 0f)
        )
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            view,
            scaleXValuesHolder,
            scaleYValuesHolder,
            rotateValuesHolder
        )
        objectAnimator.duration = duration
        objectAnimator.start()
    }

    class TabEntity:CustomTabEntity{
        var title: String =""
        var selectedIcon = 0
        var unSelectedIcon = 0

        constructor(unSelectedIcon:Int,selectedIcon:Int,title:String){
            this.title=title
            this.selectedIcon=selectedIcon
            this.unSelectedIcon=unSelectedIcon
        }

        override fun getTabUnselectedIcon(): Int {
            return unSelectedIcon
        }

        override fun getTabSelectedIcon(): Int {
            return selectedIcon
        }

        override fun getTabTitle(): String {
            return title
        }

    }

}
