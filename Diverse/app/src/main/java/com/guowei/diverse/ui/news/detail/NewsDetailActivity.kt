package com.guowei.diverse.ui.news.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.ActivityNewsDetailBinding
import com.guowei.diverse.model.learn.NewestModel
import kotlinx.android.synthetic.main.activity_news_detail.*
import me.goldze.mvvmhabit.base.BaseActivity

class NewsDetailActivity : BaseActivity<ActivityNewsDetailBinding,NewsDetailViewModel>() {



    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_news_detail
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    override fun initData() {
        val entity = intent.getParcelableExtra<NewestModel.DatasBean>("entity")
        title_content.text = entity.title
        wv_title.text = entity.title
        title_left_icon.setOnClickListener { finish() }
        viewModel.requestVideoDetail(entity.link)
        viewModel.getDetail().observe(this, Observer {
            wv_content.settings.javaScriptEnabled = true
            wv_content.addJavascriptInterface(ShowPicRelation(this),"ChayChan" )
            val htmlPart1 = "<!DOCTYPE HTML html>\n" +
                    "<head><meta charset=\"utf-8\"/>\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no\"/>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<style> \n" +
                    "img{width:100%!important;height:auto!important}\n" +
                    " </style>"
            val htmlPart2 = "</body></html>"
            val html = htmlPart1 + it?.content + htmlPart2
            wv_content.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
            wv_content.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    addJs(view)//添加多JS代码，为图片绑定点击函数

                }
            }
        })

    }

    /**添加JS代码，获取所有图片的链接以及为图片设置点击事件 */
    private fun addJs(wv: WebView) {
        wv.loadUrl("javascript:(function  pic(){" +
                "var imgList = \"\";" +
                "var imgs = document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<imgs.length;i++){" +
                "var img = imgs[i];" +
                "imgList = imgList + img.src +\";\";" +
                "img.onclick = function(){" +
                "window.chaychan.openImg(this.src);" +
                "}" +
                "}" +
                "window.chaychan.getImgArray(imgList);" +
                "})()")
    }


}
