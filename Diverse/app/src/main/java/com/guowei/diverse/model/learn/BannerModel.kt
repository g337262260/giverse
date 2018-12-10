package com.guowei.diverse.model.learn

import android.os.Parcel
import android.os.Parcelable

/**
 * Writer：GuoWei_aoj on 2018/12/5 0005 16:55
 * description:
 */
class BannerModel() : Parcelable {


    /**
     * desc :
     * id : 3
     * imagePath : http://www.wanandroid.com/blogimgs/fb0ea461-e00a-482b-814f-4faca5761427.png
     * isVisible : 1
     * order : 1
     * title : 兄弟，要不要挑个项目学习下?
     * type : 1
     * url : http://www.wanandroid.com/project
     */

    var desc: String? = null
    var id: Int = 0
    var imagePath: String? = null
    var isVisible: Int = 0
    var order: Int = 0
    var title: String? = null
    var type: Int = 0
    var url: String? = null

    constructor(parcel: Parcel) : this() {
        desc = parcel.readString()
        id = parcel.readInt()
        imagePath = parcel.readString()
        isVisible = parcel.readInt()
        order = parcel.readInt()
        title = parcel.readString()
        type = parcel.readInt()
        url = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(desc)
        parcel.writeInt(id)
        parcel.writeString(imagePath)
        parcel.writeInt(isVisible)
        parcel.writeInt(order)
        parcel.writeString(title)
        parcel.writeInt(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BannerModel> {
        override fun createFromParcel(parcel: Parcel): BannerModel {
            return BannerModel(parcel)
        }

        override fun newArray(size: Int): Array<BannerModel?> {
            return arrayOfNulls(size)
        }
    }
}
