package com.guowei.diverse.ui.learn.navigation

import android.content.Context
import android.support.v4.content.ContextCompat
import com.guowei.diverse.R

import com.guowei.diverse.model.learn.NavigationModel
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView


class NavigationTabAdapter(context: Context?, list: List<NavigationModel>) : TabAdapter {

    private var context: Context = context!!
    private var list = mutableListOf<NavigationModel>()

    init {
        this.list = list as MutableList<NavigationModel>
    }

    override fun getIcon(position: Int): ITabView.TabIcon? = null


    override fun getBadge(position: Int): ITabView.TabBadge? = null

    override fun getBackground(position: Int): Int = -1

    override fun getTitle(position: Int): ITabView.TabTitle {
        return ITabView.TabTitle.Builder()
                .setContent(list[position].name)
                .setTextColor(ContextCompat.getColor(context, R.color.colorAccent),
                        ContextCompat.getColor(context, R.color.Grey500))
                .build()
    }

    override fun getCount(): Int = list.size

}