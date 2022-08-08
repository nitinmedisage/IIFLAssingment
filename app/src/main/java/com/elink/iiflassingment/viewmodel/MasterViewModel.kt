package com.elink.iiflassingment.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elink.assingmentmedisage.roomdatabase.MasterRoomSingleton
import com.elink.iiflassingment.model.ChildWatchListEntity
import com.elink.iiflassingment.model.WatchListEntity
import com.elink.iiflassingment.repository.MasterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

/**
 * Created by Nitin Sabale
 * @since 03-08-2022. 11:59
 * this class use for to perform business logic here
 */
class MasterViewModel(var application: Application) : ViewModel() {

    private val repository: MasterRepository

    init {
        val dao = MasterRoomSingleton.getInstance(application).masterDao()
        repository = MasterRepository(dao)
    }

    /**
     * to insert the child data in to table
     */
    fun insert(data: ChildWatchListEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(data)
    }
    /**
     * to set/ update the last tread price into the database table
     * @param lastTradePrice
     * @param groupID
     */
    fun setLastTradePrice(lastTradePrice: Double, groupID: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.setLastTradePrice(lastTradePrice, groupID)
    }
    /**
     * to set / update orientation like grid or list view the into the database table
     * @param orientation
     * @param groupID
     */
    fun setOrientation(orientation: String, groupID: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.setOrientation(orientation, groupID)
    }
    /**
     * to set / update volume the into the database table
     * @param isAsc
     * @param groupID
     * @param sortBy
     */
    fun setVolumeSort(isAsc: Int?, groupID: String, sortBy: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.setVolumeSort(isAsc, groupID, sortBy)
    }
    /**
     * to set / update last tread price  the into the database table
     * @param isAsc
     * @param groupID
     * @param sortBy
     */
    fun setLastTradePriceSort(isAsc: Int?, groupID: String, sortBy: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.setLastTradePriceSort(isAsc, groupID, sortBy)
    }
    /**
     * to set / update plcose the into the database table
     * @param isAsc
     * @param groupID
     * @param sortBy
     */
    fun setPCloseSort(isAsc: Int?, groupID: String, sortBy: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.setPCloseSort(isAsc, groupID, sortBy)
    }
    /**
     * to get all the child list data from the database table
     * @param groupID
     */
    fun getAllWatchList(groupID: String): LiveData<List<ChildWatchListEntity>> {
        var allWatchList: LiveData<List<ChildWatchListEntity>> = repository.getAllWatchList(groupID)
        return allWatchList
    }
    /**
     * to check is watch list data present in database table or not
     * @param groupID
     */
    fun isWatchListExist(groupID: String): LiveData<Boolean> {
        var isWatchListExist: LiveData<Boolean> = repository.isWatchListExist(groupID)
        return isWatchListExist
    }
    /**
     * to get all the sorted data by volume child list data from the database table
     * @param isVolumeAsc
     * @param groupID
     */
    fun getAllSortedByVolume(isVolumeAsc: Int?, groupID: String): LiveData<List<ChildWatchListEntity>> {
        var sortedVolumeList: LiveData<List<ChildWatchListEntity>> = repository.getAllSortedByVolume(isVolumeAsc, groupID)
        return sortedVolumeList
    }
    /**
     * to get all the sorted by last trade price child list data from the database table
     * @param isLastTradePriceAsc
     * @param groupID
     */
    fun getAllSortedByLastTradePrice(isLastTradePriceAsc: Int?, groupID: String): LiveData<List<ChildWatchListEntity>> {
        var sortedVolumeList: LiveData<List<ChildWatchListEntity>> = repository.getAllSortedByLastTradePrice(isLastTradePriceAsc, groupID)
        return sortedVolumeList
    }
    /**
     * to get all the sorted by pclose child list data from the database table
     * @param PClose
     * @param groupID
     */
    fun getAllSortedByPClose(PClose: Int?, groupID: String): LiveData<List<ChildWatchListEntity>> {
        var sortedVolumeList: LiveData<List<ChildWatchListEntity>> = repository.getAllSortedByPClose(PClose, groupID)
        return sortedVolumeList
    }
    /**
     * to get main watch list data from the database table
     * @param context
     */
    fun getWatchList(context: Context): MutableLiveData<ArrayList<WatchListEntity>> {
        var watchList = repository.getMasterWatchListLiveData(context)
        return watchList
    }
    /**
     * to get main watch child list data from the database table
     * @param context
     * @param groupID
     */
    fun getWatchListOne(context: Context, groupID: String): MutableLiveData<ArrayList<ChildWatchListEntity>> {
        var watchListDetails = repository.getMasterWatchListDetailsLiveData(context, groupID)
        return watchListDetails
    }
}