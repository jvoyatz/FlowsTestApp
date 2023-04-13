package com.example.flowstestapp.db

import androidx.room.*
import com.example.flowstestapp.db.entities.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("select * from $TABLE_NAME")
    fun getAllItems(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: Item)

    @Delete
    suspend fun removeItem(item: Item)
}