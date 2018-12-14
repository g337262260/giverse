package com.guowei.diverse.ui.news.newslist


import android.databinding.Observable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.FragmentNewsListBinding
import me.goldze.mvvmhabit.base.BaseFragment


class NewsListFragment : BaseFragment<FragmentNewsListBinding,NewsListViewModel>() {


    //static
    companion object {
        fun newInstance(apiUrl: String) = NewsListFragment().apply { arguments = Bundle().apply { putString("CHANNEL_CODE", apiUrl) } }
    }
    override fun initContentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): Int {
        return R.layout.fragment_news_list
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        val category = arguments?.getString("CHANNEL_CODE")
        category?.let { viewModel.requestNetWork(it) }
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
