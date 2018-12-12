package com.guowei.diverse.ui.kaiyan.video

import ImageLoader
import android.app.Activity
import android.os.Bundle
import android.view.View
import cn.jzvd.Jzvd
import com.google.gson.Gson
import com.guowei.diverse.R
import com.guowei.diverse.api.Api
import com.guowei.diverse.app.NetWorkManager
import com.guowei.diverse.model.eye.ColumnPage
import com.guowei.diverse.model.eye.Data
import com.guowei.diverse.model.eye.Item
import com.guowei.diverse.model.eye.item.VideoSmallCard
import com.guowei.diverse.util.TransformerUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_video_play.*
import me.goldze.mvvmhabit.utils.KLog
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class VideoPlayActivity : Activity() {


    //data from intent
    private lateinit var videoId: String                  //视频标志ID
    private lateinit var videoTitle: String               //视频标题
    private lateinit var videoPlayUrl: String             //视频播放地址Url
    private lateinit var videoFeedUrl: String             //视频封面地址Url
    private lateinit var blurredBackgroundUrl: String     //高斯模糊背景图片Url


    //data from net request
    private var mHeaderData: Data? = null
    private var mRelatedDataList: List<Item>? = null

    //other
    private  var mAdapter: VideoPlayAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        parseIntent()
        initView()

        requestData(videoId)
    }

    private fun parseIntent() {
        videoId = intent.getStringExtra("VIDEO_ID")
        videoTitle = intent.getStringExtra("VIDEO_TITLE")
        videoFeedUrl = intent.getStringExtra("VIDEO_FEED_URL")
        videoPlayUrl = intent.getStringExtra("VIDEO_PLAY_URL")
        blurredBackgroundUrl = intent.getStringExtra("VIDEO_BG")
    }

    private fun initView() {


        //背景图片
        ImageLoader.loadNetBitmap(this, blurredBackgroundUrl) { root.background = it }
        mAdapter = VideoPlayAdapter(this)
        //视频播放器初始化
        with(videoPlayer) {
            setUp(videoPlayUrl, videoTitle, cn.jzvd.Jzvd.SCREEN_WINDOW_NORMAL)
            ImageLoader.loadNetImage(context, thumbImageView, videoFeedUrl)
        }


        //列表
        with(videoRecycler) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            visibility = android.view.View.GONE
            //列表点击事件
            adapter = mAdapter?.apply {
                onItemClick = { position -> changePlayVideo(position) }
            }
        }
    }

    private fun changePlayVideo(position: Int) {


        //init data
        val jsonObject = mRelatedDataList!![position].data
        val videoData = Gson().fromJson(jsonObject, VideoSmallCard::class.java)

        ImageLoader.loadNetBitmap(this, videoData.cover.blurred) { root.background = it }      //整个页面背景图片
        ImageLoader.loadNetImage(this, videoPlayer.thumbImageView, videoData.cover.detail)   //视频封面

        //改变播放视频Url
        videoPlayer.changeUrl(videoData.playUrl, videoData.title, 0)
        videoPlayer.startVideo()

        //刷新列表前重置状态
        videoRecycler.visibility = View.GONE
        videoRecycler.scrollToPosition(0)

        //获取数据
        requestData(videoData.id)
    }
    private fun requestData(videoId: String) {

        RetrofitUrlManager.getInstance().putDomain(Api.KAIYANAPP, Api.APP_KAIYAN)
        val videoRelated = NetWorkManager.getInstance().kaiyanService.getVideoRelated(videoId)
        val videoDetail = NetWorkManager.getInstance().kaiyanService.getVideoDetail(videoId)

        Observable.merge(videoDetail,videoRelated)
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe(object : Observer<Any> {
                    override fun onNext(resultBean: Any) {
                        KLog.d("onNext",""+resultBean)
                        when (resultBean) {
                            is Data -> mHeaderData = resultBean
                            is ColumnPage -> mRelatedDataList = resultBean.itemList.filterNot { it.type != "videoSmallCard" }
                        }

                    }

                    override fun onComplete() {
                        KLog.d("onComplete")

                        videoRecycler.visibility = View.VISIBLE
                        mAdapter?.setData(mHeaderData!!, mRelatedDataList!!)
                    }
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                    }
                })


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
