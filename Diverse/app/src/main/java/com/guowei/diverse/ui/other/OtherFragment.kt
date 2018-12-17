package com.guowei.diverse.ui.other


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.LinearLayout
import com.guowei.diverse.R
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.fragment_other.*


/**
 * A simple [Fragment] subclass.
 */
class OtherFragment : Fragment() {

    private var mAgentWeb: AgentWeb? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebContent, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.webview_error_view, -1)
                .createAgentWeb()
                .ready()
                .go("https://www.douban.com/")
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
        //返回按钮
        other_back.setOnClickListener {
            mWebView.goBack()
        }
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



}
