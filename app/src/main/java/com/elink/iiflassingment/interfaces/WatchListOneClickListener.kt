package com.elink.iiflassingment.interfaces

import com.elink.iiflassingment.model.ChildWatchListEntity

/**
 * Created by Nitin Sabale
 * @since 03-08-2022. 13:21
 * this interface use for watch list click event
 */
interface WatchListOneClickListener {
    fun onWatchListItemClickListener(data: ChildWatchListEntity)
}