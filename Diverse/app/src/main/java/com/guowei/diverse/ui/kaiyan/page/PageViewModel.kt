package com.guowei.diverse.ui.kaiyan.page

import android.annotation.SuppressLint
import android.app.Application
import android.databinding.ObservableBoolean
import android.support.v7.widget.RecyclerView
import com.guowei.diverse.api.Api
import com.guowei.diverse.app.Constant
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.app.ViewTypeEnum
import com.guowei.diverse.model.eye.ColumnPage
import com.guowei.diverse.model.eye.Item
import com.guowei.diverse.util.TransformerUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.utils.ToastUtils
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

/**
 * Writer：GuoWei_aoj on 2018/12/10 0010 14:17
 * description:
 */
class PageViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var url :String
    private lateinit var next_url :String
    @SuppressLint("StaticFieldLeak")
    lateinit var recyclerView :RecyclerView
    private var mItemList: List<Item>? = null
    private var mAdapter: PagerAdapter? = null

    var uc = UIChangeObservable()

    //下拉刷新
    var onRefreshCommand = BindingCommand<BindingAction>(BindingAction {

        requestNetWork(url)})
    //上拉加载
    var onLoadMoreCommand = BindingCommand<BindingAction>(BindingAction {
        requestNetWork(next_url)
    })

    inner class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing = ObservableBoolean(false)
        //上拉加载完成
        var finishLoadmore = ObservableBoolean(false)
    }

    fun requestNetWork(url:String) {
        RetrofitUrlManager.getInstance().putDomain(Api.KAIYANAPP, Api.APP_KAIYAN)
        NetWorkManager
                .getInstance()
                .kaiyanService
                .getColumnHomePage(url)
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<ColumnPage> {
                    override fun onNext(t: ColumnPage) {
                        mItemList = t.itemList.filter {
                            Constant.ViewTypeList.contains(ViewTypeEnum.getViewTypeEnum(it.type))
                        }
                        mAdapter?.setData(mItemList as ArrayList<Item>, false)
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

    var recycler: BindingCommand<RecyclerView> = BindingCommand(BindingConsumer {
        recyclerView -> this@PageViewModel.recyclerView = recyclerView
        recyclerView.adapter = mAdapter
    })
}