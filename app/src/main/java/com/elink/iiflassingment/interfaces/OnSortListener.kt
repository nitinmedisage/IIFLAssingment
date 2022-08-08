package com.elink.iiflassingment.interfaces

import com.elink.iiflassingment.model.WatchListEntity

/**
 * Created by Nitin Sabale
 * @since 07-08-2022. 23:04
 * this interface using for sort the data coming from database
 */
interface OnSortListener {
    fun onSortListener(code: Int)
}