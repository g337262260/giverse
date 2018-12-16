package com.guowei.diverse.ui.news.newslist

import android.os.Bundle
import com.guowei.diverse.model.learn.NewestModel
import com.guowei.diverse.model.toutiao.CommentData
import com.guowei.diverse.ui.learn.read.ReadActivity
import com.guowei.diverse.ui.news.detail.VideoDetailViewModel

import me.goldze.mvvmhabit.base.ItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand

/**
 * Writer：GuoWei_aoj on 2018/12/5 0005 09:51
 * description:
 */
class CommentItemViewModel : ItemViewModel<VideoDetailViewModel> {


    lateinit var entity: CommentData

    constructor(viewModel: VideoDetailViewModel, entity: CommentData) : super(viewModel) {
        this.entity = entity
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
    }

    constructor(viewModel: VideoDetailViewModel) : super(viewModel)

}
