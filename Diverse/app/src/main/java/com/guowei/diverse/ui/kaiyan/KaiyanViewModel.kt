package com.guowei.diverse.ui.kaiyan

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.guowei.diverse.api.Api
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.eye.Columns
import com.guowei.diverse.util.TransformerUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.goldze.mvvmhabit.base.BaseViewModel
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

/**
 * Writer：GuoWei_aoj on 2018/12/10 0010 11:14
 * description:
 */

class KaiyanViewModel(application: Application) : BaseViewModel(application) {


    var columns = MutableLiveData<Columns>()

    fun requestTabList(){
        RetrofitUrlManager.getInstance().putDomain(Api.KAIYANAPP, Api.APP_KAIYAN)
        NetWorkManager
                .getInstance()
                .kaiyanService
                .getColumnList()
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<Columns> {
                    override fun onNext(t: Columns) {
                        columns.postValue(t)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {

                    }
                })
    }

    fun getColumns(): LiveData<Columns> {
        return this.columns
    }

}