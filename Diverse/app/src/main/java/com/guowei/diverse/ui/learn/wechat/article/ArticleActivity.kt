package com.guowei.diverse.ui.learn.wechat.article

import android.databinding.Observable
import android.os.Bundle
import android.view.View
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.ActivityArticleBinding
import com.guowei.diverse.model.learn.Author
import kotlinx.android.synthetic.main.title_bar.*
import me.goldze.mvvmhabit.base.BaseActivity
import me.goldze.mvvmhabit.utils.KLog

class ArticleActivity : BaseActivity<ActivityArticleBinding,ArticleViewModel>() {


    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_article
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        val entity = intent.getParcelableExtra<Author>("entity")
        KLog.d("entity",entity)
        viewModel.requestNetWork(entity.id)
        title_left_icon.visibility = View.VISIBLE
        title_left_icon.setOnClickListener { finish() }
        title_content.text = entity.name
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
