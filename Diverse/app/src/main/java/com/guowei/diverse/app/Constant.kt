package com.guowei.diverse.app

/**
 * 文件： Constant
 * 描述： 一些常量
 * 作者： YangJunQuan   2018-8-17.
 */

object Constant {

    //允许展示的列表Item项UI类型
    val ViewTypeList = listOf(
            ViewTypeEnum.TextCard, ViewTypeEnum.BriefCard, ViewTypeEnum.DynamicInfoCard,
            ViewTypeEnum.FollowCard, ViewTypeEnum.VideoSmallCard, ViewTypeEnum.AutoPlayFollowCard)

    /**已选中频道的json */
    val SELECTED_CHANNEL_JSON = "selectedChannelJson"
    /**w未选频道的json */
    val UNSELECTED_CHANNEL_JSON = "unselectChannelJson"

    val ARTICLE_GENRE_VIDEO = "video"
    val ARTICLE_GENRE_AD = "ad"
    val TAG_MOVIE = "video_movie"

}
