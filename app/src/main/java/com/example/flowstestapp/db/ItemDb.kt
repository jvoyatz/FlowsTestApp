package com.example.flowstestapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flowstestapp.db.entities.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDb: RoomDatabase(){

    abstract fun itemDao(): ItemDao
}