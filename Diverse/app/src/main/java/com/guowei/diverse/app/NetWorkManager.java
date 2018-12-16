/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.guowei.diverse.app;

import android.content.Context;
import android.databinding.adapters.Converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guowei.diverse.BuildConfig;
import com.guowei.diverse.api.KaiyanService;
import com.guowei.diverse.api.LearnService;
import com.guowei.diverse.api.NewsService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import me.goldze.mvvmhabit.http.NetworkUtil;
import me.goldze.mvvmhabit.http.cookie.CookieJarImpl;
import me.goldze.mvvmhabit.http.cookie.store.PersistentCookieStore;
import me.goldze.mvvmhabit.http.interceptor.CacheInterceptor;
import me.goldze.mvvmhabit.http.interceptor.logging.Level;
import me.goldze.mvvmhabit.http.interceptor.logging.LoggingInterceptor;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.Utils;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.guowei.diverse.api.Api.APP_DEFAULT_DOMAIN;



/**
 * 网络请求管理，动态切换baseurl
 * @author GuoWei_aoj
 */
public class NetWorkManager {
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private LearnService mLearnService;
    private KaiyanService mKaiyanService;
    private NewsService mNewsService;

    private static Context mContext = Utils.getContext();
    //缓存时间
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;



    private static class NetWorkManagerHolder {
        private static final NetWorkManager INSTANCE = new NetWorkManager();
    }

    public static final NetWorkManager getInstance() {
        return NetWorkManagerHolder.INSTANCE;
    }

    /**请求访问quest和response拦截器*/
    private Interceptor mLogInterceptor = chain -> {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        KLog.e("----------Request Start----------------");
        KLog.e("| " + request.toString());
        KLog.json("| Response:" + content);
        KLog.e("----------Request End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    };
    /**增加头部信息的拦截器*/
    private Interceptor mHeaderInterceptor = chain -> {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547");
        builder.addHeader("Cache-Control", "max-age=0");
        builder.addHeader("Upgrade-Insecure-Requests", "1");
        builder.addHeader("X-Requested-With", "XMLHttpRequest");
        builder.addHeader("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187");
        return chain.proceed(builder.build());
    };

    //缓存配置
    private Interceptor mCacheInterceptor = chain -> {

        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();

        Request request = chain.request();
        if (!NetworkUtil.isNetworkAvailable(Utils.getContext())) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetworkUtil.isNetworkAvailable(Utils.getContext())) {
            int maxAge = 0; // read from cache
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };
    private NetWorkManager() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        //RetrofitUrlManager 初始化
        this.mOkHttpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
                .addInterceptor(new LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("Request")
                        .response("Response")
                        .addHeader("log-header", "I am the log request header.")
                        .build())
                .addInterceptor(mCacheInterceptor)
                .addInterceptor(mHeaderInterceptor)
                .addInterceptor(chain ->{
                    Request originRequest = chain.request();
                    HttpUrl originUrl = originRequest.url();
                    HttpUrl newUrl = originUrl.newBuilder()
                            .addQueryParameter("udid", "435865baacfc49499632ea13c5a78f944c2f28aa")
                            .build();
                    Request newRequest = originRequest.newBuilder().url(newUrl).build();
                    return chain.proceed(newRequest);
                })
                .addInterceptor(new CacheInterceptor(mContext))
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                .build();
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(APP_DEFAULT_DOMAIN)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .client(mOkHttpClient)
                .build();

        this.mLearnService = mRetrofit.create(LearnService.class);
        this.mKaiyanService = mRetrofit.create(KaiyanService.class);
        this.mNewsService = mRetrofit.create(NewsService.class);

    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public LearnService getLearnService() {
        return mLearnService;
    }
    public KaiyanService getKaiyanService() {
        return mKaiyanService;
    }
    public NewsService getNewsService() {
        return mNewsService;
    }

}
