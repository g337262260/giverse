package com.guowei.diverse.ui.learn.project

import android.app.Application
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.api.Api
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.ApiResponse
import com.guowei.diverse.model.learn.NewestModel
import com.guowei.diverse.model.learn.ProjectModel
import com.guowei.diverse.util.TransformerUtil
import com.zhy.view.flowlayout.TagFlowLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.utils.ToastUtils
import me.goldze.mvvmhabit.utils.Utils
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * Writer：GuoWei_aoj on 2018/12/7 0007 16:17
 * description:
 */
class ProjectViewModel(application: Application) : BaseViewModel(application) {

    private var pageIndex = 0
    private var cid = 0
    lateinit var pros: List<ProjectModel>

    lateinit var tfl :TagFlowLayout

    var uc = UIChangeObservable()

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<ProjectItemViewModel> = ObservableArrayList()
    //给RecyclerView添加ItemBinding
    var itemBinding = ItemBinding.of<ProjectItemViewModel>(BR.viewModel, R.layout.item_project)
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    val adapter = BindingRecyclerViewAdapter<ProjectItemViewModel>()

    //下拉刷新
    var onRefreshCommand = BindingCommand<BindingAction>(BindingAction {
        pageIndex = 0
        requestNetWork(this.cid)
        requestProject()
    })
    //上拉加载
    var onLoadMoreCommand = BindingCommand<BindingAction>(BindingAction {
        requestNetWork(this.cid)
    })



    inner class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing = ObservableBoolean(false)
        //上拉加载完成
        var finishLoadmore = ObservableBoolean(false)
    }


    fun requestNetWork(cid :Int) {
        this.cid = cid
        RetrofitUrlManager.getInstance().putDomain(Api.WANANDROID, Api.APP_DEFAULT_DOMAIN)
        NetWorkManager
                .getInstance()
                .learnService
                .getProItem(pageIndex,cid)
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<ApiResponse<NewestModel>> {
                    override fun onNext(t: ApiResponse<NewestModel>) {
                        if (pageIndex==0){
                            observableList.clear()
                        }
                        for (item in t.data.datas) {
                            val itemViewModel = ProjectItemViewModel(this@ProjectViewModel, item)
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

    fun requestProject(){
        RetrofitUrlManager.getInstance().putDomain(Api.WANANDROID, Api.APP_DEFAULT_DOMAIN)
        NetWorkManager
                .getInstance()
                .learnService
                .getProjects()
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<ApiResponse<List<ProjectModel>>> {
                    override fun onNext(t: ApiResponse<List<ProjectModel>>) {
                        if (t.errorCode==0) {
                            pros = t.data
                            tfl.adapter = ProjectTagAdapter(Utils.getContext(), t.data)
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {

                    }
                })
    }
    var tagFlowLayout: BindingCommand<TagFlowLayout> = BindingCommand(BindingConsumer {
        tfl -> this@ProjectViewModel.tfl = tfl
        tfl.setOnTagClickListener({ _, pos, _ ->
            requestNetWork(pros[pos].id)
            true
        })
    })
}