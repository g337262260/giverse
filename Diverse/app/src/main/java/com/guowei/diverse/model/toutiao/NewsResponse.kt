package com.guowei.diverse.model.toutiao

import com.google.gson.Gson


class NewsResponse {


    var login_status: Int = 0
    var total_number: Int = 0
    var has_more: Boolean = false
    var post_content_hint: String? = null
    var show_et_status: Int = 0
    var feed_flag: Int = 0
    var action_to_last_stick: Int = 0
    var message: String? = null
    var has_more_to_refresh: Boolean = false
    var tips: TipEntity? = null
    var data: MutableList<NewsData>? = null

    override fun toString(): String {
        return Gson().toJson(this)
    }
}
