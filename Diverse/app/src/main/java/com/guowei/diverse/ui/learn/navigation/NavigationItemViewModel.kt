package com.guowei.diverse.ui.learn.navigation

import android.os.Bundle
import com.guowei.diverse.model.learn.NavigationModel
import com.guowei.diverse.model.learn.NewestModel
import com.guowei.diverse.ui.learn.read.ReadActivity
import com.zhy.view.flowlayout.TagFlowLayout
import me.goldze.mvvmhabit.base.ItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.utils.Utils.getContext

/**
 * Writer：GuoWei_aoj on 2018/12/5 0005 09:51
 * description:
 */
class NavigationItemViewModel : ItemViewModel<NavigationViewModel> {


    lateinit var entity: NavigationModel
    lateinit var tfl :TagFlowLayout

    //条目的点击事件
    var itemClick = BindingCommand<BindingAction>(BindingAction {

    })

    //条目的长按事件
    var itemLongClick = BindingCommand<BindingAction>(BindingAction { })


    constructor(viewModel: NavigationViewModel, entity: NavigationModel) : super(viewModel) {
        this.entity = entity

    }

    constructor(viewModel: NavigationViewModel) : super(viewModel)

    var tagFlowLayout: BindingCommand<TagFlowLayout> = BindingCommand(BindingConsumer {
        tfl -> this@NavigationItemViewModel.tfl = tfl
        tfl.adapter = NavigationTagAdapter(getContext(), entity.articles)
        tfl.setOnTagClickListener({ _, pos, _ ->
            val mBundle = Bundle()
            val data = NewestModel.DatasBean()
            data.title = entity.articles[pos].title
            data.link = entity.articles[pos].link
            mBundle.putParcelable("entity", data)
            viewModel.startActivity(ReadActivity::class.java,mBundle)
            true
        })
    })

}
