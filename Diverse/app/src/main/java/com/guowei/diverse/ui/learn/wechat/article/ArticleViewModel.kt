package com.guowei.diverse.ui.learn.wechat.article

import android.app.Application
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.api.Api.APP_DEFAULT_DOMAIN
import com.guowei.diverse.api.Api.WANANDROID
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.ApiResponse
import com.guowei.diverse.model.learn.NewestModel
import com.guowei.diverse.util.TransformerUtil.Companion.getDefaultTransformer
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.ToastUtils
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * Writer：GuoWei_aoj on 2018/12/5 0005 09:18
 * description: 公众号文章列表
 */
class ArticleViewModel(application: Application) : BaseViewModel(application) {

    private var pageIndex = 0
    private var aid = 0

    var uc = UIChangeObservable()

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<ArticleItemViewModel> = ObservableArrayList()
    //给RecyclerView添加ItemBinding
    var itemBinding = ItemBinding.of<ArticleItemViewModel>(BR.viewModel, R.layout.item_wechat_article)
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    val adapter = BindingRecyclerViewAdapter<ArticleItemViewModel>()

    //下拉刷新
    var onRefreshCommand = BindingCommand<BindingAction>(BindingAction {
        pageIndex = 0
        requestNetWork(this.aid)})
    //上拉加载
    var onLoadMoreCommand = BindingCommand<BindingAction>(BindingAction {
        requestNetWork(this.aid)
    })



    inner class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing = ObservableBoolean(false)
        //上拉加载完成
        var finishLoadmore = ObservableBoolean(false)
    }


    fun requestNetWork(id :Int) {
        this.aid = id
        RetrofitUrlManager.getInstance().putDomain(WANANDROID, APP_DEFAULT_DOMAIN)
        NetWorkManager
                .getInstance()
                .learnService
                .getAuthorArticle(id,pageIndex)
                .compose(getDefaultTransformer())
                .subscribe(object : Observer<ApiResponse<NewestModel>> {
                    override fun onNext(t: ApiResponse<NewestModel>) {
//                        uc.finishLoadmore.set(!uc.finishRefreshing.get())
                        if (pageIndex==0){
                            observableList.clear()
                        }
                        for (item in t.data.datas) {
                            val itemViewModel = ArticleItemViewModel(this@ArticleViewModel, item)
                            //双向绑定动态添加Item
                            observableList.add(itemViewModel)
                        }
                        pageIndex = t.data.curPage
                    }

                    override fun onError(e: Throwable) {
                        //关闭对话框
                        dismissDialog()
                        //请求刷新完成收回
                        uc.finishRefreshing.set(!uc.finishRefreshing.get())
                        ToastUtils.showShort(e.message)
                        e.printStackTrace()
                    }

                    override fun onSubscribe(d: Disposable) {
//                        ToastUtils.showShort("下拉刷新")
                    }

                    override fun onComplete() {
                        dismissDialog()
                        uc.finishRefreshing.set(!uc.finishRefreshing.get())
                    }
                })
    }

}
