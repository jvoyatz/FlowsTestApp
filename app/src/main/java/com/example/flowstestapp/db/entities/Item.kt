package com.example.flowstestapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.flowstestapp.db.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Item(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "item_name") val itemName: String
)