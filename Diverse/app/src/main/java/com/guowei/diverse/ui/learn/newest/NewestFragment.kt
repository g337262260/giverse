package com.guowei.diverse.ui.learn.newest


import android.arch.lifecycle.Observer
import android.databinding.Observable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.FragmentNewestBinding
import com.guowei.diverse.model.BannerModel
import com.guowei.diverse.model.NewestModel
import com.guowei.diverse.ui.learn.read.ReadActivity
import com.guowei.diverse.util.GlideImageLoader
import com.youth.banner.BannerConfig
import me.goldze.mvvmhabit.base.BaseFragment
import java.util.*


class NewestFragment : BaseFragment<FragmentNewestBinding, NewestViewModel>() {


    private var listBanner: List<BannerModel>? = null

    override fun initContentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): Int {
        return R.layout.fragment_newest
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {

        binding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        binding.banner.setImageLoader(GlideImageLoader())
        binding.banner.setOnBannerListener({ position ->
            val mBundle = Bundle()
            val entity = NewestModel.DatasBean()
            listBanner?.let {
                entity.title = it[position].title
                entity.link = it[position].url
            }
            mBundle.putParcelable("entity", entity)
            viewModel.startActivity(ReadActivity::class.java,mBundle)
        })
        //请求网络数据
        viewModel.requestNetWork()
        viewModel.requestBanner()
        viewModel.getBanner().observe(this, Observer {
            updateBanner(it)
        })

    }

    /**
     * 更新banner
     * @param data
     */
    private fun updateBanner(data: List<BannerModel>?) {
        if (data != null && data.isNotEmpty()) {
            listBanner = data
            val urls = ArrayList<String>()
            val titles = ArrayList<String>()
            for (banner in data){
                banner.title?.let { titles.add(it) }
                banner.imagePath?.let { urls.add(it) }
            }
            binding.banner.visibility = View.VISIBLE
            binding.banner.setBannerTitles(titles)
            binding.banner.setImages(urls)
            binding.banner.start()
        } else {
            binding.banner.visibility = View.GONE
        }
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

    override fun onStart() {
        super.onStart()
        binding.banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        binding.banner.stopAutoPlay()
    }
}
