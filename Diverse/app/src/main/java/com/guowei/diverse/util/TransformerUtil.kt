package com.guowei.diverse.util

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Writer：GuoWei_aoj on 2018/12/6 0006 14:40
 * description:
 */
class TransformerUtil{

    companion object {
         fun <T> getDefaultTransformer(): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }




}
