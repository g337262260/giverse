package com.guowei.diverse.model.learn

import android.os.Parcel
import android.os.Parcelable

/**
 * Writer：GuoWei_aoj on 2018/12/7 0007 11:23
 * description:
 */
class Author () : Parcelable {


    /**
     * children : []
     * courseId : 13
     * id : 408
     * name : 鸿洋
     * order : 190000
     * parentChapterId : 407
     * userControlSetTop : false
     * visible : 1
     */

    var courseId: Int = 0
    var id: Int = 0
    var name: String? = null
    var order: Int = 0
    var parentChapterId: Int = 0
    var isUserControlSetTop: Boolean = false
    var visible: Int = 0
    var children: List<*>? = null

    constructor(parcel: Parcel) : this() {
        courseId = parcel.readInt()
        id = parcel.readInt()
        name = parcel.readString()
        order = parcel.readInt()
        parentChapterId = parcel.readInt()
        isUserControlSetTop = parcel.readByte() != 0.toByte()
        visible = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(courseId)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(order)
        parcel.writeInt(parentChapterId)
        parcel.writeByte(if (isUserControlSetTop) 1 else 0)
        parcel.writeInt(visible)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Author> {
        override fun createFromParcel(parcel: Parcel): Author {
            return Author(parcel)
        }

        override fun newArray(size: Int): Array<Author?> {
            return arrayOfNulls(size)
        }
    }


}
