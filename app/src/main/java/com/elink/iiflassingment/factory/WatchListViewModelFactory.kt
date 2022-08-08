package com.elink.iiflassingment.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elink.iiflassingment.viewmodel.MasterViewModel

/**
 * Created by Nitin Sabale
 * @since 03-08-2022. 12:13
 * Watch list view model factory
 * @param application
 *
 */
class WatchListViewModelFactory(val application : Application) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MasterViewModel(application) as T
    }

}