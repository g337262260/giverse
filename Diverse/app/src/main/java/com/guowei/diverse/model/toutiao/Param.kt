package com.guowei.diverse.model.toutiao

/**
 * Writer：GuoWei_aoj on 2018/12/14 0014 09:36
 * description:一些有关头条的参数，需要额外提取
 */

data class Param(
        //评论数量
        val comment_count: String,
        //position of item
        val position:Int,
        //新闻类型
        val category:String,
        //视频时长
        val videoLength:String,
        //新闻时间
        val behot_time:String
)

