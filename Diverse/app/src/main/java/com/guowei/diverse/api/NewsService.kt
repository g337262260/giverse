package com.guowei.diverse.api

import com.guowei.diverse.model.toutiao.*
import io.reactivex.Observable
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Writer：GuoWei_aoj on 2018/12/12 0012 16:10
 * description:
 */
interface NewsService {


    /**
     * 获取新闻列表
     *
     * @param category 频道
     * @return
     */
    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.TOUTIAO)
    @GET("api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752")
    abstract fun getNewsList(@Query("category") category: String, @Query("min_behot_time") lastTime: Long, @Query("last_refresh_sub_entrance_interval") currentTime: Long): Observable<NewsResponse>


    /**
     * 获取新闻详情
     */
    @GET
    abstract fun getNewsDetail(@Url url: String): Observable<ResultResponse<NewsDetail>>

    /**
     * 获取评论列表数据
     *
     * @param groupId
     * @param itemId
     * @param offset
     * @param count
     * @return
     */
    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.TOUTIAO)
    @GET("article/v2/tab_comments/")
    abstract fun getComment(@Query("group_id") groupId: String, @Query("item_id") itemId: String, @Query("offset") offset: String, @Query("count") count: String): Observable<CommentResponse>


    /**
     * 获取视频页的html代码
     */
    @GET
    abstract fun getVideoHtml(@Url url: String): Observable<String>

    /**
     * 获取视频数据json
     * @param url
     * @return
     */
    @GET
    abstract fun getVideoData(@Url url: String): Observable<ResultResponse<VideoModel>>

}