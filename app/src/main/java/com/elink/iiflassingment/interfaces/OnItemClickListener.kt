package com.elink.iiflassingment.interfaces

import com.elink.iiflassingment.model.WatchListEntity

/**
 * Created by Nitin Sabale
 * @since 03-08-2022. 13:21
 * On item click listener  interface
 */
interface OnItemClickListener {
    fun onItemClickListener(data: WatchListEntity)
}