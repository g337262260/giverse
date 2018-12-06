package com.guowei.diverse.ui.learn.tree


import android.databinding.Observable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.FragmentTreeBinding
import me.goldze.mvvmhabit.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class TreeFragment : BaseFragment<FragmentTreeBinding,TreeViewModel>() {

    override fun initContentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): Int {
        return R.layout.fragment_tree
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        viewModel.requestTree()
    }

    override fun initViewObservable() {
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                //结束刷新
                binding.twinklingRefreshLayout.finishRefreshing()
            }
        })
    }
}
