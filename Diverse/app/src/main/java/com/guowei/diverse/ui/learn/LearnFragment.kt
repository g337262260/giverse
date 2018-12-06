package com.guowei.diverse.ui.learn

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guowei.diverse.R
import com.guowei.diverse.ui.learn.newest.NewestFragment
import com.guowei.diverse.ui.learn.tree.TreeFragment
import com.guowei.diverse.ui.news.NewsFragment
import kotlinx.android.synthetic.main.fragment_learn.*
import java.util.*

/**
 * Writer：GuoWei_aoj on 2018/12/4 0004 09:37
 * description:
 */
class LearnFragment : Fragment() {

    private var mFragments: ArrayList<Fragment>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        val titles = arrayOf("最新文章","知识体系","公众号","导航","项目").toList()
        mFragments = ArrayList()
        mFragments?.run {
            add(NewestFragment())
            add(TreeFragment())
            add(NewsFragment())
            add(NewsFragment())
            add(NewsFragment())
        }
        learn_viewpager.adapter = activity?.let { LearnFragmentAdapter(childFragmentManager, it, mFragments!!,titles) }
        learn_tab.setupWithViewPager(learn_viewpager)
    }

}

