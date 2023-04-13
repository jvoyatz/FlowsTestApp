package com.example.flowstestapp

import com.example.flowstestapp.db.ItemDao
import com.example.flowstestapp.db.ItemDaoIF
import com.example.flowstestapp.db.entities.Item
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach

class ItemDaoIfImpl: ItemDaoIF{
    val flow = MutableSharedFlow<List<Item>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    private val list: MutableList<Item> = mutableListOf()

    override fun getData(): Flow<List<Item>> {
        return flow
    }

    override suspend fun empty(){
        flow.emit(listOf())
    }

    override suspend fun addItem(item: Item) {
        list.add(item)
        println("updated list $list")
        flow.emit(list)
    }

    override suspend fun addItems(items: List<Item>) {
        list.clear()
        list.addAll(items)
        println("emit new list $list")
        flow.emit(list)
    }
}

class ItemRepository2(
    val itemDao: ItemDaoIF,
    val itemNetworkSourceIF: ItemNetworkSourceIF
): ItemRepository2IF {

    override fun getData(): Flow<List<Item>> {
        return itemDao.getData()
            .onEach {
                println("1111111111111111111 $it")
                if(it.isEmpty()){
                    println("fetching...")
                    val remoteData = itemNetworkSourceIF.getRemoteData()
//                    remoteData.onEach { remoteItem ->
//                        println("adding!!")
//                        itemDao.addItems(remoteItem)
//                    }
                    itemDao.addItems(remoteData)
                }
            }
    }
}

interface ItemRepository2IF{
    fun getData(): Flow<List<Item>>
}