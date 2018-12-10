package com.guowei.diverse.ui.learn.navigation

import android.annotation.SuppressLint
import android.app.Application
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.api.Api
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.ApiResponse
import com.guowei.diverse.model.learn.NavigationModel
import com.guowei.diverse.util.TransformerUtil.Companion.getDefaultTransformer
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.utils.ToastUtils
import me.goldze.mvvmhabit.utils.Utils
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter
import me.tatarka.bindingcollectionadapter2.ItemBinding
import q.rorbin.verticaltablayout.VerticalTabLayout


/**
 * Writer：GuoWei_aoj on 2018/12/7 0007 11:55
 * description:
 */

class NavigationViewModel(application: Application) : BaseViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    lateinit var vtb : VerticalTabLayout

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<NavigationItemViewModel> = ObservableArrayList()
    //给RecyclerView添加ItemBinding
    var itemBinding = ItemBinding.of<NavigationItemViewModel>(BR.viewModel, R.layout.item_navigation)
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    val adapter = BindingRecyclerViewAdapter<NavigationItemViewModel>()


    var verticaltablayout: BindingCommand<VerticalTabLayout> = BindingCommand(BindingConsumer {
        vtb -> this@NavigationViewModel.vtb = vtb

    })


    fun requestNetWork() {
        RetrofitUrlManager.getInstance().putDomain(Api.WANANDROID, Api.APP_DEFAULT_DOMAIN)
        NetWorkManager
                .getInstance()
                .learnService
                .getNavigation()
                .compose(getDefaultTransformer())
                .subscribe(object : Observer<ApiResponse<List<NavigationModel>>> {
                    override fun onNext(t: ApiResponse<List<NavigationModel>>) {
                        vtb.setTabAdapter(NavigationTabAdapter(Utils.getContext(),t.data))
                        for (item in t.data) {
                            val itemViewModel = NavigationItemViewModel(this@NavigationViewModel, item)
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
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {
                        dismissDialog()
                    }
                })
    }
}