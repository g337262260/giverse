package com.guowei.diverse.ui.kaiyan.page


import android.arch.lifecycle.Observer
import android.databinding.Observable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.app.Constant
import com.guowei.diverse.app.ViewTypeEnum
import com.guowei.diverse.databinding.FragmentPageBinding
import com.guowei.diverse.model.eye.Item
import me.goldze.mvvmhabit.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class PageFragment : BaseFragment<FragmentPageBinding,PageViewModel>() {

    private var firstPageUrl: String? = null
    private var mItemList: List<Item>? = null
    private var loadMore:Boolean = false

    //other
    private lateinit var mAdapter: PagerAdapter

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

        mAdapter = PagerAdapter(context!!)
        binding.newestRecycler.adapter = mAdapter

        firstPageUrl = arguments?.getString("API_URL")
        firstPageUrl?.let { viewModel.requestNetWork(it) }
        viewModel.getPage().observe(this, Observer { it ->
            mItemList = it?.itemList?.filter {
                Constant.ViewTypeList.contains(ViewTypeEnum.getViewTypeEnum(it.type))
            }
            mAdapter.setData(mItemList as ArrayList<Item>, loadMore)
        })
    }

    override fun initViewObservable() {
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                //结束刷新
                binding.twinklingRefreshLayout.finishRefreshing()
                loadMore = false
            }
        })
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                //结束刷新
                binding.twinklingRefreshLayout.finishLoadmore()
                loadMore = true
            }
        })
    }

}
