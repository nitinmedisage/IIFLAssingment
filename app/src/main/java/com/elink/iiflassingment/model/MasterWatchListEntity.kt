package com.elink.iiflassingment.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

/**
 * Created by Nitin Sabale
 * @since 03-08-2022. 11:41
 * this data class use for to check json file status ans MWname
 */
data class MasterWatchListEntity (
    @SerializedName("Status")
    val Status: Int? = null,

    @SerializedName("MWName")
    val MWNameList: ArrayList<WatchListEntity>? = null
)
