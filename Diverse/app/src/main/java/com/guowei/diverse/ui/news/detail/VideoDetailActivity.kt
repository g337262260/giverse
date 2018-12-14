package com.guowei.diverse.ui.news.detail

import ImageLoader
import android.os.Bundle
import cn.jzvd.Jzvd
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.ActivityVideoDetailBinding
import kotlinx.android.synthetic.main.activity_video_play.*
import me.goldze.mvvmhabit.base.BaseActivity
import me.goldze.mvvmhabit.utils.KLog

class VideoDetailActivity : BaseActivity<ActivityVideoDetailBinding,VideoDetailViewModel>() {

    //data from intent
    private lateinit var videoId: String                  //视频标志ID
    private lateinit var videoTitle: String               //视频标题
    private lateinit var videoPlayUrl: String             //视频播放地址Url
    private lateinit var blurredBackgroundUrl: String     //高斯模糊背景图片Url

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_video_detail
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        parseIntent()
//        initView()
    }
    private fun parseIntent() {

        videoId = intent.getStringExtra("VIDEO_ID")
        videoTitle = intent.getStringExtra("VIDEO_TITLE")
        videoPlayUrl = intent.getStringExtra("VIDEO_PLAY_URL")
        blurredBackgroundUrl = intent.getStringExtra("VIDEO_BG")
        KLog.d("VIDEO_PLAY_URL",videoPlayUrl)
        viewModel.requestVideoDetail(videoPlayUrl)
    }

    private fun initView() {


        //背景图片
        ImageLoader.loadNetBitmap(this, blurredBackgroundUrl) { root.background = it }
        //视频播放器初始化
        with(videoPlayer) {
            setUp(videoPlayUrl, videoTitle, cn.jzvd.Jzvd.SCREEN_WINDOW_NORMAL)
            ImageLoader.loadNetImage(context, thumbImageView, blurredBackgroundUrl)
        }

    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) return  //大屏
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()      //释放资源
    }
}
