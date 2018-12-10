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

import com.guowei.diverse.BuildConfig;
import com.guowei.diverse.api.KaiyanService;
import com.guowei.diverse.api.LearnService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import me.goldze.mvvmhabit.http.cookie.CookieJarImpl;
import me.goldze.mvvmhabit.http.cookie.store.PersistentCookieStore;
import me.goldze.mvvmhabit.http.interceptor.CacheInterceptor;
import me.goldze.mvvmhabit.http.interceptor.logging.Level;
import me.goldze.mvvmhabit.http.interceptor.logging.LoggingInterceptor;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.Utils;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

    private static Context mContext = Utils.getContext();
    //缓存时间
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;
    private Cache cache = null;
    private File httpCacheDirectory;



    private static class NetWorkManagerHolder {
        private static final NetWorkManager INSTANCE = new NetWorkManager();
    }

    public static final NetWorkManager getInstance() {
        return NetWorkManagerHolder.INSTANCE;
    }

    private NetWorkManager() {
        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "diverse_cache");
        }
        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            KLog.e("Could not create http cache", e);
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        //RetrofitUrlManager 初始化
        this.mOkHttpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
//                .cache(cache)
                .addInterceptor(new CacheInterceptor(mContext))
                .addInterceptor(new LoggingInterceptor
                        .Builder()//构建者模式
                        //是否开启日志打印
                        .loggable(BuildConfig.DEBUG)
                        //打印的等级
                        .setLevel(Level.BASIC)
                        // 打印类型
                        .log(Platform.INFO)
                        // request的Tag
                        .request("Request")
                        // Response的Tag
                        .response("Response")
                        // 添加打印头, 注意 key 和 value 都不能是中文
                        .addHeader("log-header", "I am the log request header.")
                        .build()
                )
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                .build();

        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(APP_DEFAULT_DOMAIN)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();

        this.mLearnService = mRetrofit.create(LearnService.class);
        this.mKaiyanService = mRetrofit.create(KaiyanService.class);

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

}
