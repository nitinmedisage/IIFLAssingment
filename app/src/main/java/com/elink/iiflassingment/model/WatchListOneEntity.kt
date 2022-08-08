package com.elink.iiflassingment.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

/**
 * Created by Nitin Sabale
 * @since 03-08-2022. 11:41
 *  this class use for watch entity to match status for json data file
 */
data class WatchListOneEntity (
    @SerializedName("Status")
    val status: Int? = null,

    @SerializedName("Message")
    val message: String? = null,

    @SerializedName("MarketWatchName")
    val marketWatchName: String? = null,

    @SerializedName("Data")
    val dataList: ArrayList<ChildWatchListEntity>? = null
)
