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
import com.guowei.diverse.model.toutiao.CommentResponse
import com.guowei.diverse.model.toutiao.NewsDetail
import com.guowei.diverse.model.toutiao.Param
import com.guowei.diverse.model.toutiao.ResultResponse
import com.guowei.diverse.ui.news.newslist.CommentItemViewModel
import com.guowei.diverse.util.TimeUtil
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
 * description:头条视频播放
 */
class VideoDetailViewModel(application: Application) : BaseViewModel(application) {


    var videoUrl  = MutableLiveData<String>()
    var detail = MutableLiveData<NewsDetail>()
    private var pageNow:Int = 1
    private var groupId:String = ""
    private var itemId:String = ""
    private var size:Int = 0

    var uc = UIChangeObservable()

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<CommentItemViewModel> = ObservableArrayList()
    var itemBinding = ItemBinding.of<CommentItemViewModel>(BR.viewModel, R.layout.item_comment)
    val adapter = BindingRecyclerViewAdapter<CommentItemViewModel>()

    //上拉加载
    var onLoadMoreCommand = BindingCommand<BindingAction>(BindingAction {
        pageNow = size / Constant.COMMENT_PAGE_SIZE + 1
        requestComment(groupId,itemId)
    })

    inner class UIChangeObservable {
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
                        KLog.e("sss",t.data.content)
                        detail.postValue(t.data)
                        val decoder = object : VideoPathDecoder() {
                            override fun onDecodeError(errorMsg: String?) {

                            }

                            override fun onSuccess(url: String?) {
                                videoUrl.postValue(url)
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

    fun requestComment(groupId:String,itemId:String){
        this.groupId = groupId
        this.itemId = itemId
        val offset = (pageNow - 1) * Constant.COMMENT_PAGE_SIZE
        NetWorkManager
                .getInstance()
                .newsService
                .getComment(groupId,itemId,offset.toString(),Constant.COMMENT_PAGE_SIZE.toString())
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<CommentResponse> {
                    override fun onNext(t: CommentResponse) {
                        size = t.data.size
                        for (item in t.data) {
                            val param = Param(item.comment.digg_count.toString(),0,"","", TimeUtil.getShortTime(item.comment.create_time * 1000))
                            val itemViewModel = CommentItemViewModel(this@VideoDetailViewModel, item,param)
                            //双向绑定动态添加Item
                            observableList.add(itemViewModel)
                        }
                    }

                    override fun onError(e: Throwable) {
                        //关闭对话框
                        dismissDialog()
                        //请求刷新完成收回
                        ToastUtils.showShort(e.message)
                        e.printStackTrace()
                        uc.finishLoadmore.set(!uc.finishLoadmore.get())
                    }

                    override fun onSubscribe(d: Disposable) {
                        showDialog()

                    }

                    override fun onComplete() {
                        dismissDialog()
                        uc.finishLoadmore.set(!uc.finishLoadmore.get())
                    }
                })
    }
    fun getVideoUrl(): LiveData<String> {
        return this.videoUrl
    }
    fun getDetail():LiveData<NewsDetail>{
        return this.detail
    }
}