package com.elink.iiflassingment.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elink.iiflassingment.model.ChildWatchListEntity

/**
 * Created by Nitin Sabale
 * @since 05-08-2022. 21:42
 * this interface use for to all database query operations
 */
@Dao
interface MasterDao {
    @Query("SELECT * FROM watchListTbl WHERE GroupID=:groupID ORDER BY id ASC")
    fun allWatchList(groupID: String): LiveData<List<ChildWatchListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ChildWatchListEntity)

    @Query("SELECT EXISTS(SELECT * FROM watchListTbl WHERE GroupID=:groupID )")
    fun isWatchListExist(groupID: String): LiveData<Boolean>

    @Query("UPDATE watchListTbl SET lastTradePrice=:lastTradePrice WHERE GroupID=:groupID")
    fun setLastTradePrice(lastTradePrice: Double, groupID: String)

    @Query("UPDATE watchListTbl SET orientation=:orientation WHERE GroupID=:groupID")
    fun setOrientation(orientation: String, groupID: String)

    @Query("UPDATE watchListTbl SET volumeSort=:isAsc ,SortBy=:sortBy WHERE GroupID=:groupID")
    fun setVolumeSort(isAsc: Int?, groupID: String, sortBy: String)

    @Query("UPDATE watchListTbl SET lastTradePriceSort=:isAsc ,SortBy=:sortBy WHERE GroupID=:groupID")
    fun setLastTradePriceSort(isAsc: Int?, groupID: String, sortBy: String)

    @Query("UPDATE watchListTbl SET PCloseSort=:isAsc ,SortBy=:sortBy WHERE GroupID=:groupID")
    fun setPCloseSort(isAsc: Int?, groupID: String, sortBy: String)

    @Query("SELECT * FROM watchListTbl WHERE GroupID=:groupID ORDER BY " +
            "CASE WHEN :isAsc = 0 THEN ID END ASC, " +
            "CASE WHEN :isAsc = 1 THEN LastTradePrice END ASC, " +
            "CASE WHEN :isAsc = 2 THEN LastTradePrice END DESC ")
    fun getAllSortedByLastTradePrice(isAsc: Int?,groupID: String): LiveData<List<ChildWatchListEntity>>


    @Query("SELECT * FROM watchListTbl WHERE GroupID=:groupID ORDER BY " +
            "CASE WHEN :isAsc = 0 THEN ID END ASC, " +
            "CASE WHEN :isAsc = 1 THEN PClose END ASC, " +
            "CASE WHEN :isAsc = 2 THEN PClose END DESC ")
    fun getAllSortedByPClose(isAsc: Int?,groupID: String): LiveData<List<ChildWatchListEntity>>


    @Query("SELECT * FROM watchListTbl WHERE GroupID=:groupID ORDER BY " +
            "CASE WHEN :isAsc = 0 THEN ID END ASC, " +
            "CASE WHEN :isAsc = 1 THEN Volume END ASC, " +
            "CASE WHEN :isAsc = 2 THEN Volume END DESC ")
    fun getAllSortedByVolume(isAsc: Int?,groupID: String): LiveData<List<ChildWatchListEntity>>
}