package com.guowei.diverse.ui.kaiyan.page

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import com.guowei.diverse.api.Api
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.eye.ColumnPage
import com.guowei.diverse.util.TransformerUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.ToastUtils
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

/**
 * Writer：GuoWei_aoj on 2018/12/10 0010 14:17
 * description:
 */
class PageViewModel(application: Application) : BaseViewModel(application) {

    private  var url : String? = null
    private  var next_url : String? = null

    var pages = MutableLiveData<ColumnPage>()

    var uc = UIChangeObservable()

    //下拉刷新
    var onRefreshCommand = BindingCommand<BindingAction>(BindingAction {
        requestNetWork(url!!)})
    //上拉加载
    var onLoadMoreCommand = BindingCommand<BindingAction>(BindingAction {
        requestNetWork(next_url!!)
    })

    inner class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing = ObservableBoolean(false)
        //上拉加载完成
        var finishLoadmore = ObservableBoolean(false)
    }

    fun requestNetWork(url:String) {
        this.url = url
        RetrofitUrlManager.getInstance().putDomain(Api.KAIYANAPP, Api.APP_KAIYAN)
        NetWorkManager
                .getInstance()
                .kaiyanService
                .getColumnHomePage(url)
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<ColumnPage> {
                    override fun onNext(t: ColumnPage) {
                        pages.postValue(t)
                        next_url = t.nextPageUrl
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

    fun getPage(): LiveData<ColumnPage> {
        return this.pages
    }
}