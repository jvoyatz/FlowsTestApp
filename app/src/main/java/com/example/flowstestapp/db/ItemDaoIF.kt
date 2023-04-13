package com.example.flowstestapp.db

import com.example.flowstestapp.db.entities.Item
import kotlinx.coroutines.flow.Flow

interface ItemDaoIF {
    fun getData(): Flow<List<Item>>

    suspend fun addItem(item: Item)
    suspend fun addItems(items: List<Item>)

    suspend fun empty();
}
