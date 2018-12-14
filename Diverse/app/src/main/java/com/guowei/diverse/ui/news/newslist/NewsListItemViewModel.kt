package com.guowei.diverse.ui.news.newslist

import ImageLoader
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.guowei.diverse.R
import com.guowei.diverse.app.Constant
import com.guowei.diverse.model.learn.NewestModel
import com.guowei.diverse.model.toutiao.News
import com.guowei.diverse.model.toutiao.Param
import com.guowei.diverse.ui.learn.read.ReadActivity
import com.guowei.diverse.widget.BorderTextView
import me.goldze.mvvmhabit.base.ItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.utils.KLog
import me.goldze.mvvmhabit.utils.Utils

/**
 * Writer：GuoWei_aoj on 2018/12/5 0005 09:51
 * description:
 */
class NewsListItemViewModel : ItemViewModel<NewsListViewModel> {


    lateinit var entity: News
    lateinit var param: Param
    //根据情况显示置顶、广告和热点的标签
    private var border: BorderTextView? = null
    private var comment:TextView? = null
    private var rightRl:RelativeLayout? = null
    private var centerRl:RelativeLayout? = null
    //标签的类型
    private val channelCodes = Utils.getContext().resources.getStringArray(R.array.channel_code)
    private var isTop:Boolean = false
    private var isHot:Boolean = false
    private var isAD:Boolean = false
    private var isMovie:Boolean = false


    //条目的点击事件
    var itemClick = BindingCommand<BindingAction>(BindingAction {
        val mBundle = Bundle()
        if (entity.has_video) {
            mBundle.putString("VIDEO_ID",entity.item_id)
            if (entity.middle_image!=null) {
                mBundle.putString("VIDEO_BG",entity.middle_image.url)
            }else if (entity.image_list!=null){
                mBundle.putString("VIDEO_BG",entity.image_list[0].url.replace("list/300x196", "large"))
            }else if(entity.video_detail_info!=null){
                mBundle.putString("VIDEO_BG",entity.video_detail_info.detail_video_large_image.url)
            }
            mBundle.putString("VIDEO_TITLE",entity.title)
            val itemId = entity.item_id
            val urlSb = StringBuffer("http://m.toutiao.com/i")
            urlSb.append(itemId).append("/info/")
            mBundle.putString("VIDEO_PLAY_URL",urlSb.toString())
//            viewModel.startActivity(VideoDetailActivity::class.java,mBundle)
        }else{
            if(entity.article_type==1){
                //web
                val newest = NewestModel.DatasBean()
                newest.title = entity.title
                newest.link = entity.article_url
                mBundle.putParcelable("entity", newest)
                viewModel.startActivity(ReadActivity::class.java,mBundle)
            }else{
                //其他新闻
//                viewModel.startActivity(NewsDetailActivity::class.java)
            }
        }
    })

    //条目的长按事件
    var itemLongClick = BindingCommand<BindingAction>(BindingAction { })

    constructor(viewModel: NewsListViewModel, entity: News,param: Param) : super(viewModel) {
        this.entity = entity
        this.param = param
        isTop = param.position == 0 && param.category == channelCodes[0] //属于置顶
        isHot = entity.hot == 1//属于热点新闻
        isAD = if (!TextUtils.isEmpty(entity.tag)) entity.tag == Constant.ARTICLE_GENRE_AD else false//属于广告新闻
        isMovie = if (!TextUtils.isEmpty(entity.tag)) entity.tag == Constant.TAG_MOVIE else false//如果是影视
    }

    constructor(viewModel: NewsListViewModel) : super(viewModel)


    //将标题TextView控件回调到ViewModel中
    var borderTextView: BindingCommand<BorderTextView> = BindingCommand(BindingConsumer {
        border -> this@NewsListItemViewModel.border = border
        setTag()
    })
    var commentTextView: BindingCommand<TextView> = BindingCommand(BindingConsumer {
        comment -> this@NewsListItemViewModel.comment = comment
        if (!isAD){
            comment?.visibility = View.VISIBLE
        }
    })
    var right_rl: BindingCommand<RelativeLayout> = BindingCommand(BindingConsumer {
        rightRl -> this@NewsListItemViewModel.rightRl = rightRl
        if (entity.has_video) {
            if(entity.middle_image!=null){
                rightRl.visibility = View.VISIBLE
            }else{
                rightRl.visibility =View.GONE
            }
        }else{
            rightRl.visibility =View.GONE
        }
    })
    @SuppressLint("SetTextI18n")
    var center_rl: BindingCommand<RelativeLayout> = BindingCommand(BindingConsumer {
        centerRl -> this@NewsListItemViewModel.centerRl = centerRl
        val iv_Play = centerRl.findViewById<ImageView>(R.id.iv_play)
        val iv_img = centerRl.findViewById<ImageView>(R.id.iv_img)
        val tv_bottom_right = centerRl.findViewById<TextView>(R.id.tv_bottom_right)
        if (entity.has_video) {
            iv_Play.visibility = View.VISIBLE
            tv_bottom_right.setCompoundDrawables(null, null, null, null)
            tv_bottom_right.text = param.videoLength
        }else{
            iv_Play.visibility = View.GONE
            if (entity.gallary_image_count==1){
                tv_bottom_right.setCompoundDrawables(null, null, null, null)
            }else{
                tv_bottom_right.setCompoundDrawables(Utils.getContext().resources.getDrawable(R.mipmap.icon_picture_group), null, null, null)
                tv_bottom_right.text = entity.gallary_image_count.toString()+"图"
            }
            val url = entity.image_list[0].url.replace("list/300x196", "large")
            ImageLoader.loadNetImage(Utils.getContext(),iv_img,url)
        }

    })

    fun setTag(){
        if (isTop||isHot||isAD){
            border?.visibility = View.VISIBLE
        }else{
            border?.visibility = View.GONE
        }
        var tagText = ""
        border?.apply {
            when {
                isTop -> {
                    tagText = Utils.getContext().resources.getString(R.string.to_top)
                    setTextColor( Utils.getContext().resources.getColor(R.color.color_F96B6B))
                }
                isHot -> {
                    tagText = Utils.getContext().resources.getString(R.string.hot)
                    setTextColor( Utils.getContext().resources.getColor(R.color.color_F96B6B))
                }
                isAD -> {
                    tagText = Utils.getContext().resources.getString(R.string.ad)
                    setTextColor( Utils.getContext().resources.getColor(R.color.color_3091D8))
                }
                isMovie -> {
                    tagText = Utils.getContext().resources.getString(R.string.tag_movie)
                    setTextColor( Utils.getContext().resources.getColor(R.color.color_F96B6B))
                }
            }
            text = tagText
            KLog.a("tagText",tagText)
        }
    }

}
