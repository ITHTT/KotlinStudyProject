package com.test.library_base.network


class  BaseResponse<T>{
    var isSuccess:Boolean =true
    var data:T?=null
    var msg:String?=""
    var code:Int=0
    var isLoadMore:Boolean=false //标记分页的时候，是否加载更多
}