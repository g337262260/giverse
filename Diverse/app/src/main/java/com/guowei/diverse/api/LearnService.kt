package com.guowei.diverse.api


import com.guowei.diverse.api.Api.WANANDROID
import io.reactivex.Observable
import me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers


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
}
