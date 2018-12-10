package com.guowei.diverse.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.ActivityMainBinding
import com.guowei.diverse.ui.learn.LearnFragment
import com.guowei.diverse.ui.kaiyan.KaiyanFragment
import com.guowei.diverse.ui.news.NewsFragment
import com.guowei.diverse.ui.other.OtherFragment
import kotlinx.android.synthetic.main.activity_read.*
import me.goldze.mvvmhabit.base.BaseActivity
import me.goldze.mvvmhabit.base.BaseViewModel
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {

    private var mFragments: ArrayList<Fragment>? = null

    private val titles = arrayOf("学习","开眼","视频","其他")

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        //初始化Fragment
        initFragment()
        //初始化底部Button
        initBottomTab()
    }

    private fun initFragment() {
        title_bar.setBackgroundColor(ContextCompat.getColor(this,R.color.color3))
        title_content.text = titles[0]
        mFragments = ArrayList()
        mFragments?.run {
            add(LearnFragment())
            add(KaiyanFragment())
            add(NewsFragment())
            add(OtherFragment())
        }
        //默认选中第一个
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, mFragments!![0])
        transaction.commitAllowingStateLoss()
    }

    private fun initBottomTab() {
        val navigationController = binding.pagerBottomTab.material()
                .addItem(R.mipmap.yingyong, titles[0],ContextCompat.getColor(this, R.color.color3))
                .addItem(R.mipmap.huanzhe, titles[1])
                .addItem(R.mipmap.xiaoxi_select, titles[2])
                .addItem(R.mipmap.wode_select, titles[3])
                .setDefaultColor(ContextCompat.getColor(this, R.color.color5))
                .build()
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(object : OnTabItemSelectedListener {
            override fun onSelected(index: Int, old: Int) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, mFragments?.get(index))
                transaction.commitAllowingStateLoss()
                title_content.text = titles[index]
            }

            override fun onRepeat(index: Int) {}
        })
    }
}
