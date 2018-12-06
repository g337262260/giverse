package com.guowei.diverse.ui.learn.tree.child

import android.os.Bundle
import com.guowei.diverse.model.NewestModel
import com.guowei.diverse.ui.learn.read.ReadActivity
import me.goldze.mvvmhabit.base.ItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand

/**
 * Writer：GuoWei_aoj on 2018/12/5 0005 09:51
 * description:
 */
class TreeChildItemViewModel : ItemViewModel<TreeChildViewModel> {


    lateinit var entity: NewestModel.DatasBean

    //条目的点击事件
    var itemClick = BindingCommand<BindingAction>(BindingAction {
        val mBundle = Bundle()
        mBundle.putParcelable("entity", entity)
        viewModel.startActivity(ReadActivity::class.java,mBundle)
    })

    //条目的长按事件
    var itemLongClick = BindingCommand<BindingAction>(BindingAction { })

    constructor(viewModel: TreeChildViewModel, entity: NewestModel.DatasBean) : super(viewModel) {
        this.entity = entity
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
    }

    constructor(viewModel: TreeChildViewModel) : super(viewModel)

}
