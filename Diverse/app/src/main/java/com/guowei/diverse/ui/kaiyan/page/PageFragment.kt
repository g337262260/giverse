package com.guowei.diverse.ui.kaiyan.page


import android.databinding.Observable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.FragmentPageBinding
import me.goldze.mvvmhabit.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class PageFragment : BaseFragment<FragmentPageBinding,PageViewModel>() {

    private var firstPageUrl: String? = null

    //static
    companion object {
        fun newInstance(apiUrl: String) = PageFragment().apply { arguments = Bundle().apply { putString("API_URL", apiUrl) } }
    }

    override fun initContentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): Int {
        return R.layout.fragment_page
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        firstPageUrl = arguments?.getString("API_URL")
        firstPageUrl?.let { viewModel.requestNetWork(it) }
    }

    override fun initViewObservable() {
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                //结束刷新
                binding.twinklingRefreshLayout.finishRefreshing()
            }
        })
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                //结束刷新
                binding.twinklingRefreshLayout.finishLoadmore()
            }
        })
    }

}
