package km.self.learningkotlin.service

import kotlin.system.measureTimeMillis
import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MultiThreadCollectionAccessTest {
  val logger: Logger = LoggerFactory.getLogger("MultiThreadCollectionAccessTest")

  private lateinit var collectionAccess: CollectionAccess
  private lateinit var utils: Utils

  @BeforeEach
  fun setUp() {
    collectionAccess = CollectionAccess()
    utils = Utils()
  }

  @Nested
  inner class ListWrite {
    val amount = 1000000

    @Test
    fun testMultiThreadListWriteWith2Partitions() {
      runBlocking { runListWrite(amount, 2) }
      val list = collectionAccess.getList()
      Assertions.assertEquals(amount, list.size)
    }

    @Test
    fun testMultiThreadListWriteWith4Partitions() {
      runBlocking { runListWrite(amount, 4) }
      val list = collectionAccess.getList()
      Assertions.assertEquals(amount, list.size)
    }

    @Test
    fun testMultiThreadListWriteWith8Partitions() {
      runBlocking { runListWrite(amount, 8) }
      val list = collectionAccess.getList()
      Assertions.assertEquals(amount, list.size)
    }

    @Test
    fun testMultiThreadListWriteWith100Partitions() {
      runBlocking { runListWrite(amount, 100) }
      val list = collectionAccess.getList()
      Assertions.assertEquals(amount, list.size)
    }
  }

  @Nested
  inner class ListRead() {
    val amount = 200000

    @Test
    @Disabled
    fun testListReadWith2Partitions() {
      val partitions = 2
      val results = runBlocking { runListRead(amount, partitions) }

      Assertions.assertEquals(amount, results.size)
    }

    @Test
    @Disabled
    fun testListReadWith4Partitions() {
      val partitions = 4
      val results = runBlocking { runListRead(amount, partitions) }

      Assertions.assertEquals(amount, results.size)
    }

    @Test
    @Disabled
    fun testListReadWith8Partitions() {
      val partitions = 8
      val results = runBlocking { runListRead(amount, partitions) }

      Assertions.assertEquals(amount, results.size)
    }

    @Test
    fun testListReadWith20Partitions() {
      val partitions = 20
      val results = runBlocking { runListRead(amount, partitions) }

      Assertions.assertEquals(amount, results.size)
    }

    @Test
    fun testListReadWith28Partitions() {
      val partitions = 28
      val results = runBlocking { runListRead(amount, partitions) }

      Assertions.assertEquals(amount, results.size)
    }

    @Test
    fun testListReadWith50Partitions() {
      val partitions = 50
      val results = runBlocking { runListRead(amount, partitions) }

      Assertions.assertEquals(amount, results.size)
    }

    @Test
    fun testListReadWith100Partitions() {
      val partitions = 100
      val results = runBlocking { runListRead(amount, partitions) }

      Assertions.assertEquals(amount, results.size)
    }

    @Test
    fun testListReadWith200Partitions() {
      val partitions = 200
      val results = runBlocking { runListRead(amount, partitions) }

      Assertions.assertEquals(amount, results.size)
    }
  }

  private suspend fun runListWrite(listSize: Int, numOfPartitions: Int) {
    val values = utils.getRandomStrings(32, listSize)
    val partitions = values.chunked(listSize / numOfPartitions)
    coroutineScope {
      partitions.forEach { list ->
        logger.info("Launch new coroutine with size ${list.size}")
        launch(Dispatchers.IO) {
          logger.info("Start list write")
          val timeToWrite = measureTimeMillis { list.forEach { collectionAccess.writeToList(it) } }
          logger.info(timeToWrite.toString())
        }
      }
    }
  }

  private suspend fun runListRead(amount: Int, numOfPartitions: Int): List<String> {
    var results: List<String>
    val values = utils.getRandomStrings(32, amount)
    values.forEach { collectionAccess.writeToList(it) }

    val list = collectionAccess.getList()
    val partitions = list.chunked(amount / numOfPartitions)

    coroutineScope {
      results =
        partitions
          .map {
            logger.info("Launch coroutine with size ${it.size}")
            async(Dispatchers.IO) { readList(it, values) }
          }
          .awaitAll()
          .flatten()
    }

    logger.info("Done!")

    return results
  }

  private fun readList(list: List<String>, values: List<String>): List<String> {
    logger.info("Start reading list")
    val results = ArrayList<String>()
    val timeToRead = measureTimeMillis {
      for (value in list) {
        val filtered = values.find { value == it }
        if (filtered != null) {
          results.add(filtered)
        }
      }
    }
    logger.info(timeToRead.toString())

    return results
  }
}
