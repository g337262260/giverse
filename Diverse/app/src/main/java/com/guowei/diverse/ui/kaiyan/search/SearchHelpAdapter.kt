package com.guowei.diverse.ui.kaiyan.search

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.guowei.diverse.R
import com.guowei.diverse.app.CommonViewHolder
import com.guowei.diverse.app.inflate
import com.guowei.diverse.databinding.ItemSearchHelpBinding

/**
 * 文件： SearchHelpAdapter
 * 描述： 搜索帮助适配器(搜索历史、搜索热词)
 * 作者： YangJunQuan   2018-8-27.
 */
class SearchHelpAdapter(private val context: Context) : RecyclerView.Adapter<CommonViewHolder>() {


    lateinit var onItemClick: (position: Int) -> Unit

    private var hotWords: List<String>? = null

    fun setData(data: List<String>?) {
        hotWords = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return parent.inflate<ItemSearchHelpBinding>(R.layout.item_search_help)
    }

    override fun getItemCount() = hotWords?.size ?: 0


    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val itemSearchHelpBinding = DataBindingUtil.getBinding<ItemSearchHelpBinding>(holder.itemView)

        with(itemSearchHelpBinding!!) {
            tvHot.text = hotWords!![position]
        }


        holder.itemView.setOnClickListener {
            onItemClick.invoke(position)
        }
    }

}