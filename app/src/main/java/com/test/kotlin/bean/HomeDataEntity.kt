package com.test.kotlin.bean


data class HomeDataEntity(
    val returnCode: Int,
    val returnData: ReturnData?,
    val returnMsg: String
)

data class ReturnData(
    val homeActivity: List<HomeActivity>,
    val homeBanner: List<HomeBanner>,
    val homeCultureApps: List<HomeCultureApp>,
    val homeCultureOrderdishes: List<HomeCultureOrderdishe>,
    val homeLive: List<HomeLive>,
    val homeRmtj: List<HomeRmtj>,
    val homeSpecial: List<Any>,
    val homeVenue: List<HomeVenue>,
    val homeVod: List<HomeVod>
)

data class HomeActivity(
    val activityDesc: String,
    val activityOrderCount: String,
    val address: String,
    val endDate: String,
    val endTime: String,
    val isAllowThirdOrder: Int,
    val isExpire: String,
    val posterAppImg: String,
    val posterImg: String,
    val rank: Int,
    val rankName: String,
    val recommendName: String,
    val resId: Int,
    val resType: String,
    val reserveNum: Int,
    val sponsor: String,
    val startDate: String,
    val startTime: String,
    val status: String,
    val surplusNum: Int,
    val thirdAppOrderUrl: Any,
    val thirdOrderUrl: Any,
    val venueName: String
)

data class HomeBanner(
    val detailId: Any,
    val detailName: String,
    val posterImg: String,
    val publishAppUrl: String,
    val publishUrl: String,
    val recommendName: String,
    val resId: Any,
    val resType: Any
)

data class HomeCultureApp(
    val cultureapp: Cultureapp,
    val isCollect: Int
)

data class HomeCultureOrderdishe(
    val isOrderService: Int,
    val labels: List<Any>,
    val service: Service
)

data class HomeLive(
    val liveState: Int,
    val posterImg: String,
    val recommendName: String,
    val resId: Int,
    val resType: String,
    val startTime: String,
    val title: String,
    val url: String,
    val viewCount: Int
)

data class HomeRmtj(
    val appUrl: String,
    val detailId: Any,
    val detailName: String,
    val pcUrl: String,
    val posterImg: String,
    val publishReason: String,
    val publishText: String,
    val recommendName: String,
    val resId: Int,
    val resType: String
)

data class HomeVenue(
    val labels: List<Label>,
    val venue: Venue,
    val venueActivity: List<VenueActivity>,
    val venueImgs: List<VenueImg>
)

data class HomeVod(
    val labels: List<LabelX>,
    val vod: Vod
)

data class Cultureapp(
    val app_android_qrcode_img_url: String,
    val app_base_number: Int,
    val app_category: String,
    val app_desc: String,
    val app_href: String,
    val app_href_mobile: String,
    val app_img_url: String,
    val app_ios_qrcode_img_url: String,
    val app_name: String,
    val create_time: Long,
    val id: Int,
    val isCollect: Int,
    val order_no: Int,
    val posterImg: String,
    val recommendName: String,
    val resId: Int,
    val resType: String,
    val update_time: Long
)

data class Service(
    val holdTime: Long,
    val isOrderService: Int,
    val posterImg: String,
    val pubOrgName: String,
    val rank: Any,
    val rankName: Any,
    val recommendName: String,
    val resId: Int,
    val resType: String
)

data class Label(
    val label_name: String
)

data class Venue(
    val address: String,
    val openTime: String,
    val posterImg: String,
    val publishReason: String,
    val rank: Any,
    val rankName: String,
    val recommendName: String,
    val resId: Int,
    val resType: String,
    val venueCapacity: String,
    val venueName: String
)

data class VenueActivity(
    val activityName: String,
    val endDate: String,
    val startDate: String
)

data class VenueImg(
    val file_root: String
)

data class LabelX(
    val label_name: String
)

data class Vod(
    val detailId: Any,
    val detailName: String,
    val posterImg: String,
    val recommendName: String,
    val resId: Int,
    val resType: String
)