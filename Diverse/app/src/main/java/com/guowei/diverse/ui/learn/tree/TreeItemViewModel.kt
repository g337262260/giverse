package com.guowei.diverse.ui.learn.tree

import com.guowei.diverse.model.TreeModel
import com.zhy.view.flowlayout.TagFlowLayout
import me.goldze.mvvmhabit.base.ItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.utils.KLog
import me.goldze.mvvmhabit.utils.Utils.getContext

/**
 * Writer：GuoWei_aoj on 2018/12/5 0005 09:51
 * description:
 */
class TreeItemViewModel : ItemViewModel<TreeViewModel> {


    lateinit var entity: TreeModel
    lateinit var tfl :TagFlowLayout

    //条目的点击事件
    var itemClick = BindingCommand<BindingAction>(BindingAction {

    })

    //条目的长按事件
    var itemLongClick = BindingCommand<BindingAction>(BindingAction { })


    constructor(viewModel: TreeViewModel, entity: TreeModel) : super(viewModel) {
        this.entity = entity

    }

    constructor(viewModel: TreeViewModel) : super(viewModel)

    var tagFlowLayout: BindingCommand<TagFlowLayout> = BindingCommand(BindingConsumer {
        tfl -> this@TreeItemViewModel.tfl = tfl
        tfl.adapter = TreeTagAdapter(getContext(), entity.children)
        tfl.setOnTagClickListener({ _, pos, _ ->
            KLog.d("treechild"+entity.children?.get(pos)?.id)
//            val mBundle = Bundle()
//            mBundle.putParcelable("entity", entity)
//            viewModel.startActivity(ReadActivity::class.java,mBundle)
            true
        })
    })

}
