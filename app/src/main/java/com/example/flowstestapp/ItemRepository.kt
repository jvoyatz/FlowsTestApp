package com.example.flowstestapp

import com.example.flowstestapp.db.ItemDao
import com.example.flowstestapp.db.ItemDaoIF
import com.example.flowstestapp.db.entities.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class ItemRepository(
    val itemDao: ItemDao,
    val itemNetworkSourceIF: ItemNetworkSourceIF
): ItemRepositoryIF {

    override fun getData(): Flow<List<Item>> {
        return itemDao.getAllItems()
            .onEach {
                println(it)
                if(it.isEmpty()){
                    println("fetching...")
                    val remoteData = itemNetworkSourceIF.getRemoteData()
                    remoteData.onEach { remoteItem ->
                        println("adding!!")
                        itemDao.addItem(remoteItem)
                    }
                }
            }
    }
}

interface ItemRepositoryIF{
    fun getData(): Flow<List<Item>>
}