package com.guowei.diverse.app

import android.app.Application
import com.guowei.diverse.BuildConfig
import com.guowei.diverse.R
import me.goldze.mvvmhabit.base.BaseApplication
import me.goldze.mvvmhabit.crash.CaocConfig
import me.goldze.mvvmhabit.utils.KLog

/**
 * Writer：GuoWei_aoj on 2018/12/4 0004 09:21
 * description:
 */

class DiverseApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG)
        //初始化全局异常崩溃
        initCrash()
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }
    }

    private fun initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
//                .restartActivity(LoginActivity::class.java) //重新启动后的activity
                //                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
                //                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply()
    }

    companion object {
        lateinit var INSTANCE: Application
    }
}