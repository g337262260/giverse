package com.guowei.diverse.ui.kaiyan.page

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import com.guowei.diverse.api.Api
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.eye.ColumnPage
import com.guowei.diverse.model.eye.PageModel
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

    var pages = MutableLiveData<PageModel>()
    private var url: String = ""
    private var next_url: String = ""
    private var loadMore: Boolean = false

    var uc = UIChangeObservable()

    //下拉刷新
    var onRefreshCommand = BindingCommand<BindingAction>(BindingAction {
        loadMore = false
        requestNetWork(url, false)
    })
    //上拉加载
    var onLoadMoreCommand = BindingCommand<BindingAction>(BindingAction {
        loadMore = true
        requestNetWork(next_url, true)
    })


    inner class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing = ObservableBoolean(false)
        //上拉加载完成
        var finishLoadmore = ObservableBoolean(false)
    }

    fun requestNetWork(url: String, loadMore: Boolean) {
        if (!loadMore) {
            this.url = url
        }
        RetrofitUrlManager.getInstance().putDomain(Api.KAIYANAPP, Api.APP_KAIYAN)
        NetWorkManager
                .getInstance()
                .kaiyanService
                .getColumnHomePage(url)
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<ColumnPage> {
                    override fun onNext(t: ColumnPage) {
                        if (loadMore) {

                        }
                        val model = PageModel(loadMore, t)
                        pages.postValue(model)
                        next_url = t.nextPageUrl
                    }

                    override fun onError(e: Throwable) {
                        //关闭对话框
                        dismissDialog()
                        //请求刷新完成收回
                        finishLoad()
                        ToastUtils.showShort(e.message)
                        e.printStackTrace()
                    }

                    override fun onSubscribe(d: Disposable) {
                        showDialog()
                    }

                    override fun onComplete() {
                        dismissDialog()
                        finishLoad()
                    }
                })
    }

    fun getPage(): LiveData<PageModel> {
        return this.pages
    }

    fun finishLoad(){
        if (loadMore) {
            uc.finishLoadmore.set(!uc.finishLoadmore.get())
        }else{
            uc.finishRefreshing.set(!uc.finishRefreshing.get())
        }
    }
}