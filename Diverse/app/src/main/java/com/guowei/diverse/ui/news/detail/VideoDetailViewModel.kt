package com.guowei.diverse.ui.news.detail

import android.app.Application
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.toutiao.NewsDetail
import com.guowei.diverse.model.toutiao.ResultResponse
import com.guowei.diverse.ui.learn.newest.NewestItemViewModel
import com.guowei.diverse.ui.news.newslist.NewsListItemViewModel
import com.guowei.diverse.util.TransformerUtil
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
 * description:
 */
class VideoDetailViewModel(application: Application) : BaseViewModel(application) {



    var uc = UIChangeObservable()

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<NewsListItemViewModel> = ObservableArrayList()
    var itemBinding = ItemBinding.of<NewestItemViewModel>(BR.viewModel, R.layout.item_newest)
    val adapter = BindingRecyclerViewAdapter<NewsListItemViewModel>()

    //上拉加载
    var onLoadMoreCommand = BindingCommand<BindingAction>(BindingAction {

    })

    inner class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing = ObservableBoolean(false)
        //上拉加载完成
        var finishLoadmore = ObservableBoolean(false)
    }

    fun requestVideoDetail(url:String) {
        NetWorkManager
                .getInstance()
                .newsService
                .getNewsDetail(url)
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<ResultResponse<NewsDetail>> {
                    override fun onNext(t: ResultResponse<NewsDetail>) {
                       KLog.d("url",t.data.url)
                        val decoder = object : VideoPathDecoder() {
                            override fun onSuccess(url: String) {
                                KLog.e("onGetNewsDetailSuccess", url)
//                                UIUtils.postTaskSafely(Runnable { playVideo(url, newsDetail) })
                            }

                            fun onDecodeError(errorMsg: String) {
//                                UIUtils.showToast(errorMsg)
                            }
                        }
                        decoder.decodePath(t.data.url)
                    }

                    override fun onError(e: Throwable) {
                        //关闭对话框
                        dismissDialog()
                        //请求刷新完成收回
                        ToastUtils.showShort(e.message)
                        e.printStackTrace()
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {
                        dismissDialog()
                    }
                })
    }

}