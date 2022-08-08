package com.elink.assingmentmedisage.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elink.iiflassingment.model.ChildWatchListEntity
import com.elink.iiflassingment.roomdatabase.MasterDao

/**
 * Created by Nitin Sabale
 * @since 27-07-2022. 19:42
 * this is database class to create a room database
 */

@Database(entities = arrayOf(ChildWatchListEntity::class), version = 1, exportSchema = false)
abstract class MasterRoomSingleton : RoomDatabase(){
    abstract fun masterDao(): MasterDao

    companion object{
        private var INSTANCE: MasterRoomSingleton? = null
        fun getInstance(context: Context): MasterRoomSingleton{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MasterRoomSingleton::class.java,
                    "watchlistroomdb")
                    .build()
            }

            return INSTANCE as MasterRoomSingleton
        }
    }
}