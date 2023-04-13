package com.example.flowstestapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.flowstestapp.db.ItemDao
import com.example.flowstestapp.db.ItemDb
import com.example.flowstestapp.db.entities.Item
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ItemDaoTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var itemDao: ItemDao
    private lateinit var itemDb: ItemDb

    private lateinit var itemRepositoryIF: ItemRepositoryIF

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        itemDb = Room
            .inMemoryDatabaseBuilder(context, ItemDb::class.java)
            .build()
        itemDao = itemDb.itemDao()
        itemRepositoryIF = ItemRepository(itemDao, ItemNetworkSource())
    }

    @After
    fun cleanup() {
        itemDb.close()
    }

    @Test
    fun addItem_shouldReturn_theItem_inFlow() = runTest {
//        val item1 = Item(uid = 1, itemName = "item 1")
//        val item2 = Item(uid = 2, itemName = "item 2")
//        itemDao.addItem(item1)
//        itemDao.addItem(item2)

        itemRepositoryIF.getData().test {
            val list = awaitItem()
            println("emittt $list")

            println( "!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ${awaitItem()}")

//            assert(list.contains(item1))
//            assert(list.contains(item2))
            cancel()
        }
    }
}