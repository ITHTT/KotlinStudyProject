package com.test.kotlin.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.kotlin.R
import com.test.kotlin.bean.HomeBanner
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter : BannerAdapter<HomeBanner, HomeBannerAdapter.BannerViewHolder> {

    constructor(dataList:MutableList<HomeBanner>) : super(dataList) {

    }

    fun updateData(datas:MutableList<HomeBanner>?){
        datas?.apply {
            if(datas.isNotEmpty()) {
                if (mDatas.isNotEmpty()) mDatas.clear()
                mDatas.addAll(datas)

                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent!!.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: HomeBanner?,
        position: Int,
        size: Int
    ) {
        holder?.apply {
            Glide.with(imageView)
                .load(data?.posterImg)
                .placeholder(R.drawable.place_holder)
                .into(imageView)
        }
    }

    class BannerViewHolder(view: View):RecyclerView.ViewHolder(view){
        var imageView:ImageView = view as ImageView

    }
}