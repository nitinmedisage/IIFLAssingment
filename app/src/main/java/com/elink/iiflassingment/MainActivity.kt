package com.elink.iiflassingment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elink.assingmentmedisage.adapter.MasterWatchListAdapter
import com.elink.iiflassingment.activity.LoginActivity
import com.elink.iiflassingment.activity.WatchListActivity
import com.elink.iiflassingment.factory.WatchListViewModelFactory
import com.elink.iiflassingment.interfaces.OnItemClickListener
import com.elink.iiflassingment.model.WatchListEntity
import com.elink.iiflassingment.utilies.Constants.GROUP_ID

import com.elink.iiflassingment.utilies.SharedPref
import com.elink.iiflassingment.utilies.Utility
import com.elink.iiflassingment.viewmodel.MasterViewModel
import java.util.ArrayList


class MainActivity : AppCompatActivity(), OnItemClickListener {
    /**
     * recycle view to set the list data
     */
    lateinit var recyclerView: RecyclerView;
    /**
     * Mutable list declaration for main watch list data
     */
    private lateinit var listUsers: MutableList<WatchListEntity>
    /**
     *  mater watch list adapter
     */
    private lateinit var adapter: MasterWatchListAdapter
    /**
     * master view model
     */
    private lateinit var userViewModel : MasterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        listUsers = mutableListOf<WatchListEntity>()
        adapter = MasterWatchListAdapter(this, listUsers,this)
        recyclerView.adapter = adapter

        userViewModel = ViewModelProviders.of(this, WatchListViewModelFactory(application)).get(MasterViewModel::class.java)


        userViewModel.getWatchList(this).observe(this,object: Observer<ArrayList<WatchListEntity>> {
            override fun onChanged(it: ArrayList<WatchListEntity>?) {
                listUsers.clear()
                it?.let { listUsers.addAll(it) }
                adapter.setAdapterData(listUsers)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                Utility.showAlertMessage(this, resources.getString(R.string.logout_msg)) {
                    SharedPref.write(SharedPref.EMAIL_KEY, "");//save string in shared preference.
                    SharedPref.write(SharedPref.PWD_KEY, "");//save int in shared preference.
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                true
            }
            R.id.action_theme -> {
                Utility.chooseThemeDialog(this, delegate.applyDayNight())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClickListener(data: WatchListEntity) {
        val intent = Intent(this,WatchListActivity::class.java)
        intent.putExtra(GROUP_ID, data.MwatchName)
        startActivity(intent)
    }
}