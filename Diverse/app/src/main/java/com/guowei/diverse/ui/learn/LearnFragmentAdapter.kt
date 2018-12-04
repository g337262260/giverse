package com.guowei.diverse.ui.learn

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Writer：GuoWei_aoj on 2018/12/4 0004 14:02
 * description:
 */
class LearnFragmentAdapter(fm: FragmentManager, private val context: Context, private val fragmentList: List<Fragment>, private val list_Title: List<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return list_Title.size
    }

    /**
     * //此方法用来显示tab上的名字
     * @param position
     * @return
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return list_Title[position]
    }


}