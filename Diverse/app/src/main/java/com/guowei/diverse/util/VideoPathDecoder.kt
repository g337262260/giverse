package com.guowei.diverse.util

import android.annotation.SuppressLint
import com.guowei.diverse.app.NetWorkManager
import me.goldze.mvvmhabit.utils.KLog
import java.util.*


/**
 * Created by Administrator on 2017/2/9 0009.
 */

abstract class VideoPathDecoder {

    private val random: String
        get() {
            val random = Random()
            val result = StringBuilder()
            for (i in 0..15) {
                result.append(random.nextInt(10))
            }
            return result.toString()
        }

    @SuppressLint("CheckResult")
    fun decodePath(srcUrl: String) {
        KLog.e(TAG, "srcUrl: $srcUrl")
        NetWorkManager
                .getInstance()
                .newsService
                .getVideoHtml(srcUrl)
                .compose(TransformerUtil.getDefaultTransformer())
                .subscribe { it ->
                    KLog.d("srcUrl",it)
                }
//        NetWorkManager.getInstance().newsService.getVideoHtml(srcUrl)
//                .compose(TransformerUtil.getDefaultTransformer())
//                .flatMap({ response ->
//                    KLog.e(TAG, "srcUrl: $response")
//                    val pattern = Pattern.compile("videoId: '(.+)'")
//                    val matcher = pattern.matcher(response)
//                    if (matcher.find()) {
//                        val videoId = matcher.group(1)
//                        KLog.e(TAG, "videoId: $videoId")
//                        //1.将/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()}，进行crc32加密。
//                        val r = random
//                        KLog.e(TAG, "r: $r")
//                        val crc32 = CRC32()
//                        val s = String.format(URL_VIDEO, videoId, r)
//                        //进行crc32加密。
//                        crc32.update(s.toByteArray())
//                        val crcString = crc32.value.toString() + ""
//                        //2.访问http://i.snssdk.com/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()}&s={crc32值}
//                        val url = "$HOST_VIDEO$s&s=$crcString"
//                        KLog.e(TAG, "s: $crcString")
//                        Log.i(TAG, url)
////                        return NetWorkManager.getInstance().getNewsService().getVideoHtml(srcUrl)
////                                .compose(TransformerUtil.Companion.getDefaultTransformer())
////                                .flatMap NetWorkManager . getInstance ().getNewsService().getVideoData(url)
//                    }
//                    null
//                } as Function<String, Observable<ResultResponse<VideoModel>>>)
//                .map(object : Function<ResultResponse<VideoModel>, Video> {
//                    override fun apply(videoModelResultResponse: ResultResponse<VideoModel>): Video? {
//                        val data = videoModelResultResponse.data.video_list
//
//                        if (data.video_3 != null) {
//                            return updateVideo(data.video_3)
//                        }
//                        if (data.video_2 != null) {
//                            return updateVideo(data.video_2)
//                        }
//                        return if (data.video_1 != null) {
//                            updateVideo(data.video_1)
//                        } else null
//                    }
//
//                    private fun getRealPath(base64: String): String {
//                        return String(Base64.decode(base64.toByteArray(), Base64.DEFAULT))
//                    }
//
//                    private fun updateVideo(video: Video): Video {
//                        //base64解码
//                        video.main_url = getRealPath(video.main_url)
//                        return video
//                    }
//                })
//                .subscribe(object : RxBusSubscriber<Video>() {
//                    override fun onEvent(video: Video) {
//                        onSuccess(video.main_url)
//                    }
//                })
    }

    abstract fun onSuccess(url: String)

    companion object {

        val TAG = VideoPathDecoder::class.java.simpleName
        val URL_VIDEO = "/video/urls/v/1/toutiao/mp4/%s?r=%s"
        val HOST_VIDEO = "http://i.snssdk.com"
    }
}
