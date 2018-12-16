package com.guowei.diverse.ui.learn.read

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.webkit.WebSettings
import android.widget.LinearLayout
import com.guowei.diverse.R
import com.guowei.diverse.model.learn.NewestModel
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_read.*


class ReadActivity : Activity() {

    private var mAgentWeb: AgentWeb? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        val entity = intent.getParcelableExtra<NewestModel.DatasBean>("entity")
        title_bar.setBackgroundColor(ContextCompat.getColor(this,R.color.color3))
        title_content.text = entity.title
        title_left_icon.setOnClickListener { finish() }
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebContent, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.webview_error_view, -1)
                .createAgentWeb()
                .ready()
                .go(entity.link)
        val mWebView = mAgentWeb!!.getWebCreator().webView
        val mSettings = mWebView.settings
        mSettings.javaScriptEnabled = true
        mSettings.setSupportZoom(true)
        mSettings.builtInZoomControls = true
        //不显示缩放按钮
        mSettings.displayZoomControls = false
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        mSettings.useWideViewPort = true
        //缩放至屏幕的大小
        mSettings.loadWithOverviewMode = true
        //自适应屏幕
        mSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    }



    override fun onPause() {
        mAgentWeb!!.getWebLifeCycle().onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb!!.getWebLifeCycle().onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb!!.getWebLifeCycle().onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return mAgentWeb!!.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event)
    }

}
