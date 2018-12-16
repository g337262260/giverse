package com.guowei.diverse.ui.news.detail

import ImageLoader
import android.arch.lifecycle.Observer
import android.os.Bundle
import cn.jzvd.Jzvd
import com.guowei.diverse.BR
import com.guowei.diverse.R
import com.guowei.diverse.databinding.ActivityVideoDetailBinding
import kotlinx.android.synthetic.main.activity_video_detail.*
import me.goldze.mvvmhabit.base.BaseActivity
import me.goldze.mvvmhabit.utils.KLog

class VideoDetailActivity : BaseActivity<ActivityVideoDetailBinding,VideoDetailViewModel>() {

    //data from intent
    private lateinit var videoId: String                  //视频标志ID
    private lateinit var groupId: String                  //视频标志ID
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
        initView(videoPlayUrl)
    }
    private fun parseIntent() {

        videoId = intent.getStringExtra("VIDEO_ITEM_ID")
        groupId = intent.getStringExtra("VIDEO_GROUP_ID")
        videoTitle = intent.getStringExtra("VIDEO_TITLE")
        videoPlayUrl = intent.getStringExtra("VIDEO_PLAY_URL")
        blurredBackgroundUrl = intent.getStringExtra("VIDEO_BG")
        viewModel.requestVideoDetail(videoPlayUrl)
        viewModel.requestComment(groupId,videoId)
        viewModel.getVideoUrl().observe(this, Observer {
            initView(it!!)
        })
        viewModel.getDetail().observe(this, Observer {
            it?.apply {
                video_detail_title.text = title
                video_detail_tag.text = detail_source
            }
        })
    }

    private fun initView(url:String) {

        //视频播放器初始化
        with(videoPlayer) {
            setUp(url, videoTitle, cn.jzvd.Jzvd.SCREEN_WINDOW_NORMAL)
            ImageLoader.loadNetImage(context, thumbImageView, blurredBackgroundUrl)
        }
        twinklingRefreshLayout.setEnableRefresh(false)
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
