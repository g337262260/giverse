package com.guowei.diverse.ui.news.newslist

import android.app.Application
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import android.text.TextUtils
import com.google.gson.Gson
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.api.Api
import com.guowei.diverse.app.Constant
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.toutiao.News
import com.guowei.diverse.model.toutiao.NewsData
import com.guowei.diverse.model.toutiao.NewsResponse
import com.guowei.diverse.model.toutiao.Param
import com.guowei.diverse.util.ListUtils
import com.guowei.diverse.util.TimeUtil
import com.guowei.diverse.util.TransformerUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.KLog
import me.goldze.mvvmhabit.utils.SPUtils
import me.goldze.mvvmhabit.utils.ToastUtils
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.OnItemBind

/**
 * Writer：GuoWei_aoj on 2018/12/13 0013 14:50
 * description:
 */
class NewsListViewModel(application: Application) : BaseViewModel(application) {

    private var lastTime: Long = 0
    private var category: String = ""
    private var loadMore: Boolean = false
    private var isRecommendChannel: Boolean = false



    var uc = UIChangeObservable()

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<NewsListItemViewModel> = ObservableArrayList()
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法

    var itemBinding = ItemBinding.of(OnItemBind<NewsListItemViewModel> { itemBinding, position, item ->
        val news = item.entity
        if (category == "video") {
            itemBinding.set(BR.viewModel, R.layout.item_text_news)
            KLog.a("video------------------",news.title)
        }else{
            if (news.has_video) {
                //有视频
                when (news.video_style) {
                    0 -> {
                        if (news.middle_image == null || TextUtils.isEmpty(news.middle_image.url)) {
                            itemBinding.set(BR.viewModel, R.layout.item_text_news)
                        }
                        itemBinding.set(BR.viewModel, R.layout.item_pic_video_news)
                    }
                    1 -> {
                        itemBinding.set(BR.viewModel, R.layout.item_text_news)
                    }
                    2 -> {
                        //居中视频
                        itemBinding.set(BR.viewModel, R.layout.item_center_pic_news)
                    }
                    3 -> {
                        itemBinding.set(BR.viewModel, R.layout.item_text_news)
                    }
                }
            } else {
                //非视频新闻
                if (!news.has_image) {
                    //纯文字新闻
                    itemBinding.set(BR.viewModel, R.layout.item_text_news)
                } else {
                    if (ListUtils.isEmpty(news.image_list)) {
                        //图片列表为空，则是右侧图片
                        itemBinding.set(BR.viewModel, R.layout.item_pic_video_news)
                    } else if (news.gallary_image_count == 3) {
                        //图片数为3，则为三图
                        itemBinding.set(BR.viewModel, R.layout.item_three_pics_news)
                    } else {
                        itemBinding.set(BR.viewModel, R.layout.item_center_pic_news)
                    }
                }
            }
        }

    })
    val adapter = BindingRecyclerViewAdapter<NewsListItemViewModel>()

    //下拉刷新
    var onRefreshCommand = BindingCommand<BindingAction>(BindingAction {
        loadMore = false
        requestNetWork(category)
    })
    //上拉加载
    var onLoadMoreCommand = BindingCommand<BindingAction>(BindingAction {
        loadMore = true
        requestNetWork(category)
    })


    inner class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing = ObservableBoolean(false)
        //上拉加载完成
        var finishLoadmore = ObservableBoolean(false)
    }

    fun requestNetWork(category: String) {
        this.category = category
        isRecommendChannel = category == ""
        lastTime = SPUtils.getInstance().getLong(category, 0)//读取对应频道下最后一次刷新的时间戳
        if (lastTime == 0L) {
            //如果为空，则是从来没有刷新过，使用当前时间戳
            lastTime = System.currentTimeMillis() / 1000
        }
        RetrofitUrlManager.getInstance().putDomain(Api.TOUTIAO, Api.APP_TOUTIAO)
        NetWorkManager
                .getInstance()
                .newsService
                .getNewsList(category, lastTime, System.currentTimeMillis() / 1000)
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<NewsResponse> {
                    override fun onNext(t: NewsResponse) {
                        if (!ListUtils.isEmpty(t.data)) {
                            if (!loadMore) {
                                observableList.clear()
                            }
                            dealRepeat(t.data!!)
                            for ((index, item) in t.data!!.withIndex()) {
                                val news = Gson().fromJson(item.content, News::class.java)
                                KLog.d("title------",news.title)
                                val param = Param(news.comment_count.toString() + "评论", index, category,
                                        TimeUtil.secToTime(news.video_duration),TimeUtil.getShortTime(news.behot_time * 1000))
                                val itemViewModel = NewsListItemViewModel(this@NewsListViewModel, news, param)
                                //双向绑定动态添加Item
                                observableList.add(itemViewModel)
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        //关闭对话框
                        dismissDialog()
                        finishLoad()
                        //请求刷新完成收回
                        ToastUtils.showShort(e.message)
                        e.printStackTrace()
                    }

                    override fun onSubscribe(d: Disposable) {
                        showDialog()
                    }

                    override fun onComplete() {
                        finishLoad()
                        lastTime = System.currentTimeMillis() / 1000
                        SPUtils.getInstance().put(category, lastTime)//保存刷新的时间戳
                        dismissDialog()
                    }
                })
    }
    fun finishLoad(){
        if (loadMore) {
            uc.finishLoadmore.set(!uc.finishLoadmore.get())
        } else {
            uc.finishRefreshing.set(!uc.finishRefreshing.get())
        }
    }

    /**
     * 处理置顶新闻和广告重复
     */
    private fun dealRepeat(newList: MutableList<NewsData>) {
        if (isRecommendChannel && !ListUtils.isEmpty(newList)) {
            //如果是推荐频道并且数据列表已经有数据,处理置顶新闻或广告重复的问题
            if (loadMore) {
                newList.removeAt(0)//由于第一条新闻是重复的，移除原有的第一条
                newList.removeAt(0)//由于第二条新闻是重复的，移除原有的第二条
            }
            //新闻列表通常第4个是广告,除了第一次有广告，再次获取的都移除广告
            if (newList.size >= 4) {
                val fourthNews = newList[3]
                val news = Gson().fromJson(fourthNews.content, News::class.java)
                //如果列表第4个和原有列表第4个新闻都是广告，并且id一致，移除
                if (news.tag!=null&&news.tag.equals(Constant.ARTICLE_GENRE_AD)) {
                    newList.remove(fourthNews)
                }
            }
        }
    }
}