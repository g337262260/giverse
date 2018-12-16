package com.guowei.diverse.util;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;


import com.guowei.diverse.R;
import com.guowei.diverse.app.NetWorkManager;
import com.guowei.diverse.model.toutiao.ResultResponse;
import com.guowei.diverse.model.toutiao.Video;
import com.guowei.diverse.model.toutiao.VideoModel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;


import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.goldze.mvvmhabit.bus.RxBusSubscriber;
import me.goldze.mvvmhabit.utils.KLog;


/**
 * Created by Administrator on 2017/2/9 0009.
 */

public abstract class VideoPathDecoder {

    private static final String TAG = VideoPathDecoder.class.getSimpleName();
    /**接口根地址*/
    public static final String BASE_SERVER_URL = "http://is.snssdk.com/";
    public static final String HOST_VIDEO = "http://i.snssdk.com";
    public static final String URL_VIDEO="/video/urls/v/1/toutiao/mp4/%s?r=%s";

    @SuppressLint("CheckResult")
    public void decodePath(String srcUrl) {
        KLog.e(TAG,"srcUrl---: " + srcUrl);

        NetWorkManager.getInstance().getNewsService().getVideoHtml(srcUrl)
                .flatMap((Function<String, Observable<ResultResponse<VideoModel>>>) response -> {
                    KLog.e(TAG,"srcUrl---: " + response);
                    Pattern pattern = Pattern.compile("videoId: '(.+)'");
                    Matcher matcher = pattern.matcher(response);
                    if (matcher.find()) {
                        String videoId = matcher.group(1);
                        KLog.e(TAG,"videoId: " + videoId);
                        //1.将/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()}，进行crc32加密。
                        String r = getRandom();
                        KLog.e(TAG,"r: " + r);
                        CRC32 crc32 = new CRC32();
                        String s = String.format(URL_VIDEO, videoId, r);
                        //进行crc32加密。
                        crc32.update(s.getBytes());
                        String crcString = crc32.getValue() + "";
                        //2.访问http://i.snssdk.com/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()}&s={crc32值}
                        String url = HOST_VIDEO + s + "&s=" + crcString;
                        KLog.e(TAG,"s: " + crcString);
                        Log.i(TAG,url);
                        return  NetWorkManager.getInstance().getNewsService().getVideoData(url);
                    }
                    return null;
                })
                .map(new Function<ResultResponse<VideoModel>, Video>() {
                    @Override
                    public Video apply(ResultResponse<VideoModel> videoModelResultResponse) {
                        VideoModel.VideoListBean data = videoModelResultResponse.data.video_list;

                        if (data.video_3 != null) {
                            return updateVideo(data.video_3);
                        }
                        if (data.video_2 != null) {
                            return updateVideo(data.video_2);
                        }
                        if (data.video_1 != null) {
                            return updateVideo(data.video_1);
                        }
                        return null;
                    }

                    private String getRealPath(String base64) {
                        return new String(Base64.decode(base64.getBytes(), Base64.DEFAULT));
                    }

                    private Video updateVideo(Video video) {
                        //base64解码
                        video.main_url = getRealPath(video.main_url);
                        return video;
                    }
                })
                .compose(TransformerUtil.Companion.getDefaultTransformer())
                .subscribe(new RxBusSubscriber<Video>() {
                    @Override
                    protected void onEvent(Video video) {
                        onSuccess(video.main_url);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onDecodeError(UIUtils.getString(R.string.video_parse_error));
                    }
                });
    }

    private String getRandom() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    public abstract void onSuccess(String url);
    public abstract void onDecodeError(String errorMsg);
}
