package com.guowei.diverse.ui.news.detail

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.app.Constant
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.learn.BannerModel
import com.guowei.diverse.model.toutiao.CommentResponse
import com.guowei.diverse.model.toutiao.NewsDetail
import com.guowei.diverse.model.toutiao.ResultResponse
import com.guowei.diverse.ui.learn.newest.NewestItemViewModel
import com.guowei.diverse.ui.news.newslist.CommentItemViewModel
import com.guowei.diverse.ui.news.newslist.NewsListItemViewModel
import com.guowei.diverse.util.TransformerUtil
import com.guowei.diverse.util.UIUtils
import com.guowei.diverse.util.VideoPathDecoder
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.KLog
import me.goldze.mvvmhabit.utils.ToastUtils
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter
import me.tatarka.bindingcollectionadapter2.ItemBinding

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
                        KLog.e("link-----",t.data.content)
                        detail.postValue(t.data)
                    }

                    override fun onError(e: Throwable) {
                        //关闭对话框
                        dismissDialog()
                        //请求刷新完成收回
                        ToastUtils.showShort(e.message)
                        KLog.e("link-----",e.message)
                        e.printStackTrace()
                    }

                    override fun onSubscribe(d: Disposable) {
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