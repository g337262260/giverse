package com.guowei.diverse.ui.news


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.guowei.diverse.R
import com.guowei.diverse.app.Constant
import com.guowei.diverse.model.toutiao.Channel
import com.guowei.diverse.ui.news.newslist.NewsListFragment
import kotlinx.android.synthetic.main.fragment_news.*
import me.goldze.mvvmhabit.utils.KLog
import me.goldze.mvvmhabit.utils.SPUtils
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    private var channelList = ArrayList<Channel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initChannelData()
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    private fun initChannelData(){
        val mGson = Gson()
        //获取新闻分类
        var selectedChannelJson = SPUtils.getInstance().getString(Constant.SELECTED_CHANNEL_JSON, "")
        if (TextUtils.isEmpty(selectedChannelJson)) {
            //本地没有title
            val channels = resources.getStringArray(R.array.channel)
            val channelCodes = resources.getStringArray(R.array.channel_code)
            //默认添加了全部频道
            for (i in channelCodes.indices) {
                val title = channels[i]
                val code = channelCodes[i]
                channelList.add(Channel(title, code))
            }
            selectedChannelJson = mGson.toJson(channelList)//将集合转换成json字符串
            SPUtils.getInstance().put(Constant.SELECTED_CHANNEL_JSON, selectedChannelJson)//保存到sp
        } else {
            //之前添加过
            val selectedChannel = mGson.fromJson<List<Channel>>(selectedChannelJson, object : TypeToken<List<Channel>>() {}.type)
            channelList.addAll(selectedChannel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout(channelList)
    }
    private fun initTabLayout(channelList:List<Channel>) {
        KLog.d("viewpager",toutiao_viewpager)
        with(toutiao_viewpager){
            adapter = object : FragmentPagerAdapter(childFragmentManager){
                override fun getItem(position: Int): Fragment {
                    val channel_code = channelList[position].channel_code
                    return NewsListFragment.newInstance(channel_code)
                }

                override fun getCount(): Int {
                    return channelList.size
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    return channelList[position].channel
                }

            }
        }

        with(toutiao_tab){
            tabMode = android.support.design.widget.TabLayout.MODE_SCROLLABLE
            setSelectedTabIndicatorColor(android.support.v4.content.ContextCompat.getColor(context, com.guowei.diverse.R.color.color3))
            setupWithViewPager(toutiao_viewpager)
        }
    }

}
