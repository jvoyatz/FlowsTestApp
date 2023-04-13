package com.example.flowstestapp

import app.cash.turbine.test
import com.example.flowstestapp.db.entities.Item
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ItemDaoTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var itemRepositoryIF: ItemRepository2IF
    val itemDao = ItemDaoIfImpl()

    @Before
    fun create() {
        itemRepositoryIF = ItemRepository2(itemDao, ItemNetworkSource())
    }

    @Test
    fun addItem_shouldReturn_theItem_inFlow_bycollect() = runTest {

        itemDao.empty()
        itemRepositoryIF.getData().collect{
            if(it.isEmpty()) return@collect
            println(it.size == 2)
            this.coroutineContext.job.cancel()
        }
    }

    @Test
    fun addItem_shouldReturn_theItem_inFlow() = runTest {
        itemRepositoryIF.getData().test {
            itemDao.empty()
            val firstEmission = awaitItem()
            println(firstEmission.isEmpty())
            Assert.assertEquals(firstEmission.isEmpty(), true)
            //   this.skipItems(1)

            val secondEmission = awaitItem()
            Assert.assertEquals(secondEmission.size, 2)
            cancel()
        }
    }

}