package com.guowei.diverse.ui.news.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.guowei.diverse.R
import kotlinx.android.synthetic.main.activity_image_view_pager.*
import me.goldze.mvvmhabit.utils.KLog
import java.util.ArrayList
import java.util.HashMap

class ImageViewPagerActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {


    private val TAG = ImageViewPagerActivity::class.java.simpleName
    companion object {
        public val IMG_URLS = "mImageUrls"
        public val POSITION = "position"
    }

    private var mImageUrls: List<String> = ArrayList()
    private var mCurrentPosition: Int = 0
    private val mFragments = ArrayList<BigImageFragment>()
    private val mDownloadingFlagMap = HashMap<Int, Boolean>()//用于保存对应位置图片是否在下载的标识


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view_pager)
        initView()
    }

    private fun initView() {
        val intent = intent
        mImageUrls = intent.getStringArrayListExtra(IMG_URLS)
        val position = intent.getIntExtra(POSITION, 0)
        mCurrentPosition = position

        for (i in mImageUrls.indices) {
            val url = mImageUrls[i]
            val imageFragment = BigImageFragment()

            val bundle = Bundle()
            bundle.putString("imgUrl", url)
            imageFragment.setArguments(bundle)

            mFragments.add(imageFragment)//添加到fragment集合中
            mDownloadingFlagMap.put(i, false)//初始化map，一开始全部的值都为false
        }

        vp_pics.setAdapter(MyPagerAdapter(supportFragmentManager))
        vp_pics.addOnPageChangeListener(this)
        tv_save.setOnClickListener {
            KLog.e("save")
        }

    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        mCurrentPosition = position

        setIndicator(mCurrentPosition)
    }

    @SuppressLint("SetTextI18n")
    private fun setIndicator(position: Int) {
        tv_indicator.setText((position + 1).toString() + "/" + mImageUrls.size)//设置当前的指示
    }
    internal inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }
    }

}
