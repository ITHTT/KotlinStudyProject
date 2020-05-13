package com.test.kotlin.bean

data class HomeNewsEntity(
    val returnCode: Int,
    val returnData: List<NewsData>,
    val returnMsg: String
)

data class NewsData(
    val content: String,
    val createTime: String,
    val imgSrc: String,
    val labelsRecords: List<Any>,
    val logicSourceId: Int,
    val title: String
)