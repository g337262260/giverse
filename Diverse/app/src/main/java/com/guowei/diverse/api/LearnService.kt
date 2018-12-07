package com.guowei.diverse.api


import com.guowei.diverse.api.Api.WANANDROID
import com.guowei.diverse.model.*
import io.reactivex.Observable
import me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Writer：GuoWei_aoj on 2018/8/6 0006 14:17
 * description: Api
 * @author GuoWei_aoj
 */
interface LearnService{
    /**
     * 如果不需要多个 BaseUrl, 继续使用初始化时传入 Retrofit 中的默认 BaseUrl, 就不要加上 DOMAIN_NAME_HEADER 这个 Header
     */
    @Headers(DOMAIN_NAME_HEADER + WANANDROID)
    /**
     * 可以通过在注解里给全路径达到使用不同的 BaseUrl, 但是这样无法在 App 运行时动态切换 BaseUrl
     */
    @GET("/wxarticle/chapters/json")
    abstract fun getUsers(): Observable<ResponseBody>

    @GET("/article/list/{page}/json")
    abstract fun getNewest(@Path("page") page: Int): Observable<ApiResponse<NewestModel>>

    @GET("/banner/json")
    abstract fun getBanner(): Observable<ApiResponse<List<BannerModel>>>

    @GET("/tree/json")
    abstract fun getTree(): Observable<ApiResponse<List<TreeModel>>>

    @GET("/article/list/{page}/json")
    abstract fun getTreeChild(@Path("page") page: Int,@Query("cid") cid: Int): Observable<ApiResponse<NewestModel>>

    @GET("/wxarticle/chapters/json")
    abstract fun getAuthor(): Observable<ApiResponse<List<Author>>>

    @GET("/wxarticle/list/{authorId}/{page}/json")
    abstract fun getAuthorArticle(@Path("authorId") id: Int,@Path("page") page: Int): Observable<ApiResponse<NewestModel>>

    @GET("/navi/json")
    abstract fun getNavigation(): Observable<ApiResponse<List<NavigationModel>>>

    @GET("/project/tree/json")
    abstract fun getProjects(): Observable<ApiResponse<List<ProjectModel>>>

    @GET("/project/list/{page}/json")
    abstract fun getProItem(@Path("page") page: Int,@Query("cid") cid: Int): Observable<ApiResponse<NewestModel>>
}
