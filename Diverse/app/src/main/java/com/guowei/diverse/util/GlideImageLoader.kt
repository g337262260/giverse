package com.guowei.diverse.util

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Writer：GuoWei_aoj on 2018/12/6 0006 10:30
 * description:
 */
class GlideImageLoader : ImageLoader() {

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).into(imageView)
    }

}
