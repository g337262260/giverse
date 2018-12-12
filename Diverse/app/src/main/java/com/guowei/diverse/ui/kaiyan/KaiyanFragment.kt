package com.guowei.diverse.ui.kaiyan

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.FragmentMediaBinding
import com.guowei.diverse.model.eye.Columns
import com.guowei.diverse.ui.kaiyan.page.PageFragment
import com.guowei.diverse.ui.kaiyan.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_media.*
import me.goldze.mvvmhabit.base.BaseFragment
import me.goldze.mvvmhabit.utils.KLog


class KaiyanFragment : BaseFragment<FragmentMediaBinding,KaiyanViewModel>() {

    private lateinit var mData: Columns

    override fun initContentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): Int {
        return R.layout.fragment_media
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        viewModel.requestTabList()
        viewModel.getColumns().observe(this, Observer {
            mData = it!!
            initTabLayout()
        })
        kaiyan_search.setOnClickListener {  SearchFragment().show(childFragmentManager, "searchFragment") }
    }

    private fun initTabLayout() {
        val tabList = mData.tabInfo.tabList
        tabList.removeAt(0)
        with(binding.kaiyanViewpager){
            adapter = object :FragmentPagerAdapter(childFragmentManager){
                override fun getItem(position: Int): Fragment {
                    val apiUrl = tabList[position].apiUrl
                    KLog.d("apiUrl",apiUrl)
                    return PageFragment.newInstance(apiUrl)
                }

                override fun getCount(): Int {
                    return tabList.size
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    return tabList[position].name
                }

            }
        }

        with(binding.kaiyanTab){
            tabMode = TabLayout.MODE_SCROLLABLE
            setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.color3))
            setupWithViewPager(binding.kaiyanViewpager)
        }
    }

}
