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

    val ZHANWEI = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545023579265&di=3c36124a3f5ab9207d680451e0af3327&imgtype=0&src=http%3A%2F%2Fdown.admin5.com%2Fuploads%2Fallimg%2F141111%2F2069_141111091611_1.png"

    /**获取评论列表每页的数目 */
    val COMMENT_PAGE_SIZE = 20

}
