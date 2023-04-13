package com.example.flowstestapp

import com.example.flowstestapp.db.entities.Item

class ItemNetworkSource : ItemNetworkSourceIF {

    override fun getRemoteData(): List<Item> {
        return listOf(
            Item(1, "remoteUid1" ),
            Item(2, "remoteUid2" )
        )
    }
}

interface ItemNetworkSourceIF{
    fun getRemoteData() : List<Item>
}