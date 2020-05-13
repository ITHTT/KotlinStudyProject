package com.test.kotlin.ui.adapter

import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.test.kotlin.R
import com.test.kotlin.bean.HomeRmtj

class HomeRecommendAdapter: BaseQuickAdapter<HomeRmtj, BaseViewHolder>(R.layout.layout_home_recommend_item) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var holder= super.onCreateViewHolder(parent, viewType)

        var width= (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(20f)) / 9 * 4
        holder.itemView.layoutParams.width=width
        holder.itemView.layoutParams.height=width*3/5
        holder.itemView.requestLayout()

        return holder
    }

    override fun convert(holder: BaseViewHolder, item: HomeRmtj) {
        var ivImg=holder.getView<ImageView>(R.id.iv_img)
        Glide.with(ivImg)
            .load(item.posterImg)
            .placeholder(R.drawable.place_holder)
            .into(ivImg)

        if (TextUtils.isEmpty(item.publishText)) {
            setTags(holder.getView(R.id.tv_tag),item.resType)
        } else {
            setTags(holder.getView(R.id.tv_tag),item.publishText)
        }
    }

    private fun setTags(tvTag: TextView, type: String?) {
        val myGrad = tvTag.background as GradientDrawable
        var tags: String? = ""
        when (type) {
            "DIC_RESOURCE_TYPE_1", "视听空间" -> {
                tags = "视听空间"
                myGrad.setColor(ColorUtils.getColor(R.color.tag_vod))
            }
            "DIC_RESOURCE_TYPE_5", "新闻资讯" -> {
                tags = "新闻资讯"
                myGrad.setColor(ColorUtils.getColor(R.color.tag_news))
            }
            "精彩资讯" -> {
                tags = "精彩资讯"
                myGrad.setColor(ColorUtils.getColor(R.color.tag_news))
            }
            "DIC_RESOURCE_TYPE_7", "活动预约" -> {
                tags = "活动预约"
                myGrad.setColor(ColorUtils.getColor(R.color.tag_activity))
            }
            "DIC_RESOURCE_TYPE_8", "共享直播" -> {
                tags = "共享直播"
                myGrad.setColor(ColorUtils.getColor(R.color.tag_live))
            }
            "DIC_RESOURCE_TYPE_9", "场馆导航" -> {
                tags = "场馆导航"
                myGrad.setColor(ColorUtils.getColor(R.color.tag_venue))
            }
            "DIC_RESOURCE_TYPE_18", "特色应用" -> {
                tags = "特色应用"
                myGrad.setColor(ColorUtils.getColor(R.color.tag_app))
            }
            "DIC_RESOURCE_TYPE_22", "服务点单" -> {
                tags = "服务点单"
                myGrad.setColor(ColorUtils.getColor(R.color.tag_server))
            }
            else -> if (!TextUtils.isEmpty(type)) {
                tags = type
            }
        }
        tvTag.text = tags
    }
}