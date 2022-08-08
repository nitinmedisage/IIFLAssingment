package com.elink.iiflassingment.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elink.assingmentmedisage.roomdatabase.MasterRoomSingleton
import com.elink.iiflassingment.model.ChildWatchListEntity
import com.elink.iiflassingment.model.MasterWatchListEntity
import com.elink.iiflassingment.model.WatchListEntity
import com.elink.iiflassingment.model.WatchListOneEntity
import com.elink.iiflassingment.roomdatabase.MasterDao
import com.elink.iiflassingment.utilies.Constants
import com.elink.iiflassingment.utilies.Utility
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList

/**
 * Created by Nitin Sabale
 * @since 03-08-2022. 11:37
 * this is master respository class use for handle the database operatios
 */
class MasterRepository(private val masterDao: MasterDao) {

  fun getMasterWatchListLiveData(context: Context) : MutableLiveData<ArrayList<WatchListEntity>> {
        var mutableLiveData = MutableLiveData<ArrayList<WatchListEntity>>()
        val jsonFileString =
            Utility.getJsonFromAssets(context, "watchlist.json")
        val gson = Gson()
      val listType: Type = object : TypeToken<MasterWatchListEntity>() {}.type
      val listData: MasterWatchListEntity = gson.fromJson(jsonFileString, listType)
        var watchList: ArrayList<WatchListEntity>
        if (listData.Status == 0) {
            watchList = listData.MWNameList!!
            watchList!!.let { mutableLiveData.value = it }
        }
        return mutableLiveData
    }

    /**
     *  this method use for set master watch list data with multiple json files
     *  @param context
     *  @param groupID
     */
    fun getMasterWatchListDetailsLiveData(context: Context,groupID:String) : MutableLiveData<ArrayList<ChildWatchListEntity>> {
        var mutableLiveData = MutableLiveData<ArrayList<ChildWatchListEntity>>()
        var jsonFileString = ""
        if (groupID.equals(Constants.WATCHLIST_1)){
             jsonFileString = Utility.getJsonFromAssets(context, "watchlist_one.json")!!
        } else if (groupID.equals(Constants.WATCHLIST_2)){
            jsonFileString = Utility.getJsonFromAssets(context, "watchlist_two.json")!!
        } else if (groupID.equals(Constants.WATCHLIST_3)){
            jsonFileString = Utility.getJsonFromAssets(context, "watchlist_three.json")!!
        }

        val gson = Gson()
        val listType: Type = object : TypeToken<WatchListOneEntity>() {}.type
        val listData: WatchListOneEntity = gson.fromJson(jsonFileString, listType)
        var watchList: ArrayList<ChildWatchListEntity>
        if (listData.status == 0) {
            watchList = listData.dataList!!
            watchList!!.let { mutableLiveData.value = it }
        }
        return mutableLiveData
    }
    /**
     *  this method use for insert and store the data into database table when click on watch list
     *  @param data
     */
    fun insert(data:ChildWatchListEntity) {
        masterDao.insert(data)
    }
    /**
     *  this method use for to set last tread price and update into database table
     *  @param lastTradePrice
     *  @param groupID
     */
    fun setLastTradePrice(lastTradePrice: Double, groupID: String){
        masterDao.setLastTradePrice(lastTradePrice,groupID)
    }
    /**
     *  this method use for to set orientation like list or grid and update into database table
     *  @param orientation
     *  @param groupID
     */
    fun setOrientation(orientation: String, groupID: String){
        masterDao.setOrientation(orientation,groupID)
    }
    /**
     *  this method use for to set volume sort and update into database table with ascending order
     *  @param isAsc
     *  @param groupID
     *  @param sortBy
     */
    fun setVolumeSort(isAsc : Int?, groupID: String,sortBy: String){
        masterDao.setVolumeSort(isAsc,groupID,sortBy)
    }
    /**
     *  this method use for to set last tread price and update into database table with ascending order
     *  @param isAsc
     *  @param groupID'
     *  @param sortBy
     */
    fun setLastTradePriceSort(isAsc : Int?, groupID: String,sortBy: String){
        masterDao.setLastTradePriceSort(isAsc,groupID,sortBy)
    }
    /**
     *  this method use for to set plcose and update into database table with ascending order
     *  @param isAsc
     *  @param groupID'
     *  @param sortBy
     */
    fun setPCloseSort(isAsc : Int?, groupID: String,sortBy: String){
        masterDao.setPCloseSort(isAsc,groupID,sortBy)
    }
    /**
     *  this method use for to check database table is exist or not
     *  @param groupID'
     */

    fun isWatchListExist(groupID: String) : LiveData<Boolean> {
        var sortedVolumeList : LiveData<Boolean> = masterDao.isWatchListExist(groupID)
        return  sortedVolumeList
    }
    /**
     *  this method use for to get data from child watch list
     *  @param groupID'
     */
   fun getAllWatchList(groupID: String) : LiveData<List<ChildWatchListEntity>> {
        var sortedVolumeList : LiveData<List<ChildWatchListEntity>> = masterDao.allWatchList(groupID)
        return  sortedVolumeList
    }
    /**
     *  this method use for to get data all sorted data by volume
     *  @param isVolumeAsc
     *  @param groupID
     */
  fun getAllSortedByVolume(isVolumeAsc : Int?, groupID: String) : LiveData<List<ChildWatchListEntity>> {
        var sortedVolumeList : LiveData<List<ChildWatchListEntity>> = masterDao.getAllSortedByVolume(isVolumeAsc,groupID)
        return  sortedVolumeList
    }
    /**
     *  this method use for to get data all sorted data by last tread price
     *  @param isLastTradePriceAsc
     *  @param groupID
     */
    fun getAllSortedByLastTradePrice(isLastTradePriceAsc: Int?, groupID: String) : LiveData<List<ChildWatchListEntity>> {
        var sortedVolumeList : LiveData<List<ChildWatchListEntity>> = masterDao.getAllSortedByLastTradePrice(isLastTradePriceAsc,groupID)
        return  sortedVolumeList
    }
    /**
     *  this method use for to get data all sorted data by pclose
     *  @param PClose
     *  @param groupID
     */
    fun getAllSortedByPClose(PClose: Int?, groupID: String) : LiveData<List<ChildWatchListEntity>> {
        var sortedVolumeList : LiveData<List<ChildWatchListEntity>> = masterDao.getAllSortedByPClose(PClose,groupID)
        return  sortedVolumeList
    }

}