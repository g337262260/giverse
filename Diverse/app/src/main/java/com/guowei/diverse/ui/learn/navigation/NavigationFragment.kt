package com.guowei.diverse.ui.learn.navigation


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.FragmentNavigationBinding
import kotlinx.android.synthetic.main.fragment_navigation.*
import me.goldze.mvvmhabit.base.BaseFragment
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView


/**
 * A simple [Fragment] subclass.
 */
class NavigationFragment : BaseFragment<FragmentNavigationBinding,NavigationViewModel>() {


    private var bScroll: Boolean = false
    private var currentIndex: Int = 0
    private var bClickTab: Boolean = false

    /**
     * linearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }


    override fun initContentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): Int {
        return R.layout.fragment_navigation
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationRecycler.run {
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
        leftRightLink()
    }

    override fun initData() {
        viewModel.requestNetWork()
    }

    /**
     * Left TabLayout and Right RecyclerView Link
     */
    private fun leftRightLink() {
        binding.navigationRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (bScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    scrollRecyclerView()
                }
                rightLinkLeft(newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (bScroll) {
                    scrollRecyclerView()
                }
            }
        })
        binding.navigationTabLayout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabView?, position: Int) {
            }

            override fun onTabSelected(tab: TabView?, position: Int) {
                bClickTab = true
                selectTab(position)
            }
        })

    }
    /**
     * Select Left TabLayout to Smooth Right RecyclerView
     */
    private fun selectTab(position: Int) {
        currentIndex = position
        binding.navigationRecycler.stopScroll()
        smoothScrollToPosition(position)
    }

    private fun smoothScrollToPosition(position: Int) {
        val firstPosition: Int =linearLayoutManager.findFirstVisibleItemPosition()
        val lastPosition: Int = linearLayoutManager.findLastVisibleItemPosition()
        when {
            position <= firstPosition -> {
                binding.navigationRecycler.smoothScrollToPosition(position)
            }
            position <= lastPosition -> {
                val top: Int = binding.navigationRecycler.getChildAt(position - firstPosition).top
                binding.navigationRecycler.smoothScrollBy(0, top)
            }
            else -> {
                binding.navigationRecycler.smoothScrollToPosition(position)
                bScroll = true
            }
        }
    }
    private fun scrollRecyclerView() {
        bScroll = false
        val indexDistance: Int = currentIndex - linearLayoutManager.findFirstVisibleItemPosition()
        if (indexDistance > 0 && indexDistance < binding.navigationRecycler!!.childCount) {
            val top: Int = binding.navigationRecycler.getChildAt(indexDistance).top
            binding.navigationRecycler.smoothScrollBy(0, top)
        }
    }

    /**
     * Right RecyclerView link Left TabLayout
     *
     * @param newState RecyclerView Scroll State
     */
    private fun rightLinkLeft(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (bClickTab) {
                bClickTab = false
                return
            }
            val firstPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()
            if (firstPosition != currentIndex) {
                currentIndex = firstPosition
                setChecked(currentIndex)
            }
        }
    }
    /**
     * Smooth Right RecyclerView to Select Left TabLayout
     *
     * @param position checked position
     */
    private fun setChecked(position: Int) {
        if (bClickTab) {
            bClickTab = false
        } else {
            navigation_tab_layout.setTabSelected(currentIndex)
        }
        currentIndex = position
    }
}
