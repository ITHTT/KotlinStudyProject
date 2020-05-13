package com.test.kotlin.ui.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.mtjsoft.www.gridviewpager_recycleview.GridViewPager
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.test.kotlin.R
import com.test.kotlin.bean.HomeBanner
import com.test.kotlin.bean.HomeRmtj
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator

class MainHomeAdapter :DelegateAdapter.Adapter<MainHomeAdapter.MainHomeViewHolder>{
    var viewType=0
    var layoutHelper:LayoutHelper?=null
    var viewCount=0

    var bannerData:MutableList<HomeBanner>?=null

    var hGap=SizeUtils.dp2px(10f)

    var dataType=0
    var recommendData:MutableList<HomeRmtj>?=null

    var title:String?=null


    constructor(type:Int,count:Int,helper: LayoutHelper){
        this.viewType=type
        this.viewCount=count
        this.layoutHelper=helper
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHomeViewHolder {
        var holder:MainHomeViewHolder?=null
        var view:View?=null
        when(viewType){
            0x0001 -> view=LayoutInflater.from(parent.context).inflate(R.layout.layout_home_banner_item,parent,false)
            0x0002 -> view=LayoutInflater.from(parent.context).inflate(R.layout.layout_home_menu_item,parent,false)
            0x0003 -> view=LayoutInflater.from(parent.context).inflate(R.layout.layout_home_title_item,parent,false)
            0x0004 -> view=LayoutInflater.from(parent.context).inflate(R.layout.layout_home_horizontal_scroll_item,parent,false)
            0x0005 -> view=LayoutInflater.from(parent.context).inflate(R.layout.layout_home_title_more_item,parent,false)
            0x0006 -> view=LayoutInflater.from(parent.context).inflate(R.layout.layout_home_news_item,parent,false)
        }
        holder=view?.let {
            MainHomeViewHolder(it,viewType)
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return viewCount
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        return layoutHelper!!
    }

    override fun onBindViewHolder(holder: MainHomeViewHolder, position: Int) {
        when(viewType){
            0x0001 -> {

                holder.bannerAdapter?.updateData(bannerData)
                holder.banner?.start()
            }
            0x0004 -> {
                when(dataType){
                    0 -> {
                        recommendData?.apply {
                            var adapter=HomeRecommendAdapter()
                            adapter.data=this
                            holder.rv?.adapter=adapter
                        }
                    }
                }
            }
        }
    }


    class MainHomeViewHolder(view: View,type:Int) :RecyclerView.ViewHolder(view){
        var banner: Banner<HomeBanner,HomeBannerAdapter>?=null
        var bannerAdapter:HomeBannerAdapter?=null
        var gridPager:GridViewPager?=null

        var rv:RecyclerView?=null

        var tvTitle: TextView?=null

        init {
            when(type){
                0x0001->{
                    banner=view.findViewById(R.id.banner)
                    banner?.layoutParams?.width=ScreenUtils.getScreenWidth()
                    banner?.layoutParams?.height=ScreenUtils.getScreenWidth()*9/16
                    banner?.requestLayout()
                    banner?.indicator= CircleIndicator(view.context)

                    bannerAdapter= HomeBannerAdapter(arrayListOf())

                    banner?.adapter= bannerAdapter!!
                }
                0x0002-> {
                    gridPager=view.findViewById(R.id.gvp_menu)

                    var titles=view.resources.getStringArray(R.array.home_menus)

                    gridPager?.setDataAllCount(titles.size)
                    gridPager?.setImageTextLoaderInterface { imageView, textView, position ->
                        textView.text=titles[position]
                        imageView.setImageResource(R.mipmap.home_venue_icon)
                    }?.show()

                }
                0x0004 -> {
                    rv=view.findViewById(R.id.rv)
                    rv?.let {
                        var layoutManager=LinearLayoutManager(view.context)
                        layoutManager.orientation=LinearLayoutManager.HORIZONTAL

                        it.layoutManager=layoutManager

                        it.addItemDecoration(object : RecyclerView.ItemDecoration() {
                            override fun getItemOffsets(
                                outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State
                            ) {
                                super.getItemOffsets(outRect, view, parent, state)
                                var childPosition=parent.getChildAdapterPosition(view)
                                when(childPosition){
                                    0 ->{
                                        outRect.left=SizeUtils.dp2px(10f)
                                        outRect.right=SizeUtils.dp2px(3f)
                                    }
                                    parent.adapter?.itemCount?.minus(1) ->{
                                        outRect.left=SizeUtils.dp2px(3f)
                                        outRect.right=SizeUtils.dp2px(10f)
                                    }
                                    else -> {
                                        outRect.left=SizeUtils.dp2px(3f)
                                        outRect.right=SizeUtils.dp2px(3f)
                                    }
                                }
                            }
                        })
                    }
                }
            }


        }
    }
}