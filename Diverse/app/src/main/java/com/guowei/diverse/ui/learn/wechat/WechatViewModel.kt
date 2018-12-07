package com.guowei.diverse.ui.learn.wechat

import android.app.Application
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.api.Api
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.ApiResponse
import com.guowei.diverse.model.Author
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
 * Writer：GuoWei_aoj on 2018/12/7 0007 10:06
 * description:微信公众号文章
 */

class WechatViewModel(application: Application) : BaseViewModel(application) {


    var uc = UIChangeObservable()

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<WechatItemViewModel> = ObservableArrayList()
    //给RecyclerView添加ItemBinding
    var itemBinding = ItemBinding.of<WechatItemViewModel>(BR.viewModel, R.layout.item_author)
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    val adapter = BindingRecyclerViewAdapter<WechatItemViewModel>()

    //下拉刷新
    var onRefreshCommand = BindingCommand<BindingAction>(BindingAction {
        getAuthor()})


    inner class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing = ObservableBoolean(false)
    }


    fun getAuthor() {
        RetrofitUrlManager.getInstance().putDomain(Api.WANANDROID, Api.APP_DEFAULT_DOMAIN)
        NetWorkManager
                .getInstance()
                .learnService
                .getAuthor()
                .compose(getDefaultTransformer())
                .subscribe(object : Observer<ApiResponse<List<Author>>> {
                    override fun onNext(t: ApiResponse<List<Author>>) {
//                        uc.finishLoadmore.set(!uc.finishRefreshing.get())
                        observableList.clear()
                        for (item in t.data) {
                            val itemViewModel = WechatItemViewModel(this@WechatViewModel, item)
                            //双向绑定动态添加Item
                            observableList.add(itemViewModel)
                        }
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