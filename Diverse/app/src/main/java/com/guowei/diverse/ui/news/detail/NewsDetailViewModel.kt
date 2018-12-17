package com.guowei.diverse.ui.news.detail

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.toutiao.NewsDetail
import com.guowei.diverse.model.toutiao.ResultResponse
import com.guowei.diverse.util.TransformerUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.utils.KLog
import me.goldze.mvvmhabit.utils.ToastUtils

/**
 * Writer：GuoWei_aoj on 2018/12/14 0014 17:01
 * description:非视频新闻
 */
class NewsDetailViewModel(application: Application) : BaseViewModel(application) {


    var detail = MutableLiveData<NewsDetail>()

    fun requestVideoDetail(url:String) {
        KLog.e("link-----",url)
        NetWorkManager
                .getInstance()
                .newsService
                .getNewsDetail(url)
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<ResultResponse<NewsDetail>> {
                    override fun onNext(t: ResultResponse<NewsDetail>) {
                        detail.postValue(t.data)
                    }

                    override fun onError(e: Throwable) {
                        //关闭对话框
                        dismissDialog()
                        //请求刷新完成收回
                        ToastUtils.showShort(e.message)
                        e.printStackTrace()
                    }

                    override fun onSubscribe(d: Disposable) {
                        showDialog()
                    }

                    override fun onComplete() {
                        dismissDialog()
                    }
                })
    }

    fun getDetail():LiveData<NewsDetail>{
        return this.detail
    }
}