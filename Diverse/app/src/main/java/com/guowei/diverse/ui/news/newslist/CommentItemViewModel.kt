package com.guowei.diverse.ui.news.newslist

import ImageLoader
import android.widget.ImageView
import com.guowei.diverse.model.toutiao.CommentData
import com.guowei.diverse.model.toutiao.Param
import com.guowei.diverse.ui.news.detail.VideoDetailViewModel
import me.goldze.mvvmhabit.base.ItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.utils.Utils

/**
 * Writer：GuoWei_aoj on 2018/12/5 0005 09:51
 * description:
 */
class CommentItemViewModel : ItemViewModel<VideoDetailViewModel> {

    lateinit var param: Param
    lateinit var entity: CommentData
    lateinit var image:ImageView

    constructor(viewModel: VideoDetailViewModel, entity: CommentData,param: Param) : super(viewModel) {
        this.entity = entity
        this.param = param
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
    }

    constructor(viewModel: VideoDetailViewModel) : super(viewModel)

    var userImage : BindingCommand<ImageView> = BindingCommand(BindingConsumer {
        image -> this@CommentItemViewModel.image = image
        ImageLoader.loadNetCircleImage(Utils.getContext(),image,entity.comment.user_profile_image_url)
    })

}
