package com.guowei.diverse.api

import com.guowei.diverse.model.eye.ColumnPage
import com.guowei.diverse.model.eye.Columns
import com.guowei.diverse.model.eye.Data
import com.guowei.diverse.model.eye.MessageInfo
import io.reactivex.Observable
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import retrofit2.http.*

/**
 * Writer：GuoWei_aoj on 2018/12/10 0010 10:42
 * description:
 */
interface KaiyanService {

    //获取【首页】栏目列表
    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.KAIYANAPP)
    @GET("api/v5/index/tab/list")
    fun getColumnList(): Observable<Columns>


    //获取某栏目详情
    @GET
    fun getColumnHomePage(@Url url: String): Observable<ColumnPage>


    //搜索热词
    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.KAIYANAPP)

    @GET("api/v3/queries/hot")
    fun getSearchHotWord(): Observable<List<String>>


    //根据用户输入进行搜索首页,例： http://baobab.kaiyanapp.com/api/v3/search?query=关键字
    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.KAIYANAPP)
    @GET("api/v3/search")
    fun searchByKeyWord(@Query("query") query: String): Observable<ColumnPage>


    //搜索更多
    @GET
    fun searchMore(@Url nextPageUrl: String): Observable<ColumnPage>

    //获取【关注】下的tabList
    @GET("api/v5/community/tab/list")
    fun getFocusTabList(): Observable<Columns>


    //获取【关注】下某一个Tab的数据
    @GET
    fun getFocusTabInfo(@Url tabUrl: String): Observable<ColumnPage>


    //获取【通知】下的tabList
    @GET("v3/messages/tabList")
    fun getNotifyTabList(): Observable<Columns>


    //获取【通知】下某一个Tab的数据
    @GET
    fun getNotifyTabInfo(@Url tabUrl: String): Observable<MessageInfo>


    //根据视频Id获取该视频的详细信息    例子：http://baobab.kaiyanapp.com/api/v2/video/127373
    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.KAIYANAPP)
    @GET("api/v2/video/{videoId}")
    fun getVideoDetail(@Path("videoId") videoId: String): Observable<Data>


    //获取【视频播放页】一些额外信息，如该视频的相关推荐
    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.KAIYANAPP)
    @GET("api/v4/video/related")
    fun getVideoRelated(@Query("id") videoId: String): Observable<ColumnPage>
}