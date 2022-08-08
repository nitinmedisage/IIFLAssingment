package com.elink.iiflassingment.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elink.assingmentmedisage.adapter.WathListOneAdapter
import com.elink.iiflassingment.R
import com.elink.iiflassingment.bottomsheetdialog.SortBottomSheet
import com.elink.iiflassingment.bottomsheetdialog.WatchListDetailsBottomSheet
import com.elink.iiflassingment.factory.WatchListViewModelFactory
import com.elink.iiflassingment.interfaces.OnSortListener
import com.elink.iiflassingment.interfaces.WatchListOneClickListener
import com.elink.iiflassingment.model.ChildWatchListEntity
import com.elink.iiflassingment.utilies.Constants
import com.elink.iiflassingment.utilies.Constants.GROUP_ID
import com.elink.iiflassingment.utilies.Constants.LAST_TRADE_PRICE
import com.elink.iiflassingment.utilies.Constants.P_CLOSE
import com.elink.iiflassingment.utilies.Constants.VOLUME
import com.elink.iiflassingment.utilies.SharedPref
import com.elink.iiflassingment.utilies.Utility
import com.elink.iiflassingment.utilies.Utility.checkTheme
import com.elink.iiflassingment.utilies.Utility.chooseThemeDialog
import com.elink.iiflassingment.viewmodel.MasterViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_watchlist_one.*
import org.jetbrains.anko.doAsync
import java.util.*


/**
 *  Watch list activity for to check data list coming from json file
 *
 */

class WatchListActivity : AppCompatActivity(), WatchListOneClickListener, OnSortListener, View.OnClickListener {
    /**
    *  List users list for child watch list
    */
    private lateinit var listUsers: MutableList<ChildWatchListEntity>
    /**
     *  List users list adapter
     */
    private lateinit var adapter: WathListOneAdapter
    /**
     *  User view model
     */
    private lateinit var userViewModel : MasterViewModel
    /**
     *  group id for to store the group id in room database for watchlist data
     */
    lateinit var groupID :String
    /**
     *  orientation for vertical and grid view
     */
    lateinit var orientation :String
    /**
     *  sort status for orientation
     */
    var sortStatus = 0
    /**
     * handler for activity auto- refresh
     */
    var handler = Handler()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchlist_one)

        groupID = intent.getStringExtra(GROUP_ID).toString()

        linearLayoutSort.setOnClickListener(this)
        linearLayoutView.setOnClickListener(this)

        listUsers = mutableListOf<ChildWatchListEntity>()
        adapter = WathListOneAdapter(this, listUsers, this, Constants.VERTICAL)
        recyclerViewWatchListOne.adapter = adapter
        recyclerViewWatchListOne.setOnScrollChangeListener(View.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->  })
        userViewModel = ViewModelProviders.of(this, WatchListViewModelFactory(application)).get(MasterViewModel::class.java)
        // to check theme for system theme
        checkTheme(delegate.applyDayNight())
        // this method use for get data form db
        getDataFromRoomDB()
        // this method use for sync bottom sheet
        syncBootomSheet()

        handler = Handler()
        handler.postDelayed(m_Runnable, 3000)

        }
    /**
     * handler for activity auto- refresh
     */
    private val m_Runnable: Runnable = object : Runnable {
        override fun run() {
            this@WatchListActivity.handler.postDelayed(this, 3000)
            val MAX = 10
            val random = Random()
            val randomNumber = random.nextInt(MAX) * if (random.nextBoolean()) -1 else 1
            listUsers.forEach{
                doAsync {
                    val updateLastTradePrice = it.lastTradePrice!! + randomNumber
                    userViewModel.setLastTradePrice(updateLastTradePrice, groupID)
                }
            }
            adapter.updateLastTradePrice(randomNumber)
        }
    }

    /**
     *  this method use for get data form db
     */
    private fun getDataFromRoomDB() {
        userViewModel.getAllWatchList(groupID).observe(this, Observer { it ->
            listUsers.clear()
            it?.let { listUsers.addAll(it) }
            if (listUsers.size > 0) {
                isWatchListExist(listUsers)
            } else {
                getDataFromAssets()
            }
        })

    }
    /**
     *  this method use to exist watch list with status
     */
    private fun isWatchListExist(list: MutableList<ChildWatchListEntity>) {
        userViewModel.isWatchListExist(groupID).observe(this, Observer { it ->
            if (it) {
                if (list[0].SortBy.equals(Constants.VOLUME)) {
                    if (list[0].volumeSort!!.equals(1)) {
                        sortStatus = 101
                    } else {
                        sortStatus = 100
                    }
                    getAllSortedByVolume(list[0].volumeSort)
                } else if (list[0].SortBy.equals(Constants.LAST_TRADE_PRICE)) {
                    if (list[0].lastTradePriceSort!!.equals(1)) {
                        sortStatus = 201
                    } else {
                        sortStatus = 200
                    }
                    getAllSortedByLastTradePrice(list[0].lastTradePriceSort)
                } else if (list[0].SortBy.equals(Constants.P_CLOSE)) {
                    if (list[0].PCloseSort!!.equals(1)) {
                        sortStatus = 301
                    } else {
                        sortStatus = 300
                    }
                    getAllSortedByPClose(list[0].PCloseSort)
                } else {
                    sortStatus = 0
                    setRecyclerView(list[0].orientation.toString())
                }
            } else {
                // this method use for to get data form json file
                getDataFromAssets()
            }
        })
    }
    /**
     *  this method use for to get data form json file
     */
    private fun getDataFromAssets() {
        userViewModel.getWatchListOne(this, groupID).observe(this, object : Observer<ArrayList<ChildWatchListEntity>> {
            override fun onChanged(it: ArrayList<ChildWatchListEntity>?) {
                listUsers.clear()
                it?.let { listUsers.addAll(it) }
                setRecyclerView(Constants.VERTICAL)
                storeDataToRoomDB()
            }
        })
    }
    /**
     *  this method use for to store data in room database
     */
    private fun storeDataToRoomDB() {
        if(listUsers != null) {
            listUsers.forEach{
                val model = ChildWatchListEntity(it.id, groupID, Constants.VERTICAL, "NA", Constants.NA, Constants.NA, Constants.NA, it.dayHigh, it.dayLow, it.EPS, it.exch, it.exchType, it.fullName, it.high52Week, it.lastTradePrice, it.lastTradeTime, it.low52Week, it.month, it.nseBseCode, it.pClose, it.quarter, it.scripCode, it.shortName, it.volume, it.year)
                doAsync {
                    userViewModel.insert(model)
                }
            }
        }
    }
    /**
     *  this method use for to get data from room database with volume status
     *  @param isVolumeAsc
     */
    private fun getAllSortedByVolume(isVolumeAsc: Int?) {
        userViewModel.getAllSortedByVolume(isVolumeAsc, groupID).observe(this, Observer { it ->
            listUsers.clear()
            it?.let { listUsers.addAll(it) }
            setRecyclerView(listUsers[0].orientation.toString())
        })
    }
    /**
     *  this method use for to get data from room database with last tread price  status
     *  @param isLastTradePriceAsc
     */
    private fun getAllSortedByLastTradePrice(isLastTradePriceAsc: Int?) {
        userViewModel.getAllSortedByLastTradePrice(isLastTradePriceAsc, groupID).observe(this, Observer { it ->
            listUsers.clear()
            it?.let { listUsers.addAll(it) }
            setRecyclerView(listUsers[0].orientation.toString())
        })
    }
    /**
     *  this method use for to get data from room database with pclose status
     *  @param PClose
     */
    private fun getAllSortedByPClose(PClose: Int?) {
        userViewModel.getAllSortedByPClose(PClose, groupID).observe(this, Observer { it ->
            listUsers.clear()
            it?.let { listUsers.addAll(it) }
            setRecyclerView(listUsers[0].orientation.toString())
        })
    }
    /**
     *  this method use for set recycle view orientation with list and grid
     *  @param orientation
     */

    private fun setRecyclerView(orientation: String) {
        if (orientation.equals(Constants.TILE)){
            recyclerViewWatchListOne.layoutManager = GridLayoutManager(this, 2)
            adapter.setAdapterData(listUsers, orientation)
        } else {
            recyclerViewWatchListOne.layoutManager = LinearLayoutManager(this)
            adapter.setAdapterData(listUsers, orientation)
        }

    }
    /**
     *  this method use for to get data from room database with details in bottom sheet
     *  @param data
     */
    override fun onWatchListItemClickListener(data: ChildWatchListEntity) {
        val bottomSheetDialogFragment = WatchListDetailsBottomSheet()

        bottomSheetDialogFragment.arguments = Bundle().apply {  putParcelable(Constants.WATCHLIST_DETAILS, data)}
        bottomSheetDialogFragment.show(
                supportFragmentManager,
                "Bottom Sheet Dialog Fragment"
        )
    }
    /**
     *  this method use for sync bottom sheet
     */
    private fun syncBootomSheet() {
        val mBottomSheetBehaviorfilter = BottomSheetBehavior.from(bottom_sheet_sync)
        mBottomSheetBehaviorfilter.state = BottomSheetBehavior.STATE_COLLAPSED
        mBottomSheetBehaviorfilter.peekHeight = 0
        mBottomSheetBehaviorfilter.setBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                Utility.showAlertMessage(this, resources.getString(R.string.logout_msg)) {
                    SharedPref.write(SharedPref.EMAIL_KEY, "");//save string in shared preference.
                    SharedPref.write(SharedPref.PWD_KEY, "");//save int in shared preference.
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                true
            }
            R.id.action_theme -> {
                chooseThemeDialog(this, delegate.applyDayNight())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.linearLayoutSort -> {
                val sortBottomSheet = SortBottomSheet()

                sortBottomSheet.arguments = Bundle().apply { putInt(Constants.SORT_BY, sortStatus) }
                sortBottomSheet.show(
                        supportFragmentManager,
                        "Bottom Sheet Dialog Fragment"
                )

            }
            R.id.linearLayoutView -> {
                if (txtViewTitle.text.toString().equals(getString(R.string.list))) {
                    txtViewTitle.text = getString(R.string.grid)
                    ivViewTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_grid_24))
                    doAsync {
                        userViewModel.setOrientation(Constants.TILE, groupID)
                    }
                    setRecyclerView(Constants.TILE)

                } else {
                    txtViewTitle.text = getString(R.string.list)
                    ivViewTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_list_24))
                    doAsync {
                        userViewModel.setOrientation(Constants.VERTICAL, groupID)
                    }
                    setRecyclerView(Constants.VERTICAL)
                }
            }
        }
    }
    /**
     *  this method use for to sort the data coming from room data base
     *  @param code
     */
    override fun onSortListener(code: Int) {
        when(code){
            100 -> {
                doAsync {
                    userViewModel.setVolumeSort(Constants.HIGH, groupID, VOLUME)
                }

                getAllSortedByVolume(Constants.HIGH)

            }
            101 -> {
                doAsync {
                    userViewModel.setVolumeSort(Constants.LOW, groupID, VOLUME)
                }
                getAllSortedByVolume(Constants.LOW)
            }
            200 -> {
                doAsync {
                    userViewModel.setLastTradePriceSort(Constants.HIGH, groupID, LAST_TRADE_PRICE)
                }

                getAllSortedByLastTradePrice(Constants.HIGH)

            }
            201 -> {
                doAsync {
                    userViewModel.setLastTradePriceSort(Constants.LOW, groupID, LAST_TRADE_PRICE)
                }
                getAllSortedByLastTradePrice(Constants.LOW)

            }
            300 -> {
                doAsync {
                    userViewModel.setPCloseSort(Constants.HIGH, groupID, P_CLOSE)
                }

                getAllSortedByPClose(Constants.HIGH)

            }
            301 -> {
                doAsync {
                    userViewModel.setPCloseSort(Constants.LOW, groupID, P_CLOSE)
                }
                getAllSortedByPClose(Constants.LOW)

            }
        }
    }


    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(m_Runnable)
        finish()
    }
}