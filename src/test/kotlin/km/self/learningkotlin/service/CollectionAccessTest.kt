package km.self.learningkotlin.service

import kotlin.system.measureTimeMillis
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CollectionAccessTest {
  private lateinit var collectionAccess: CollectionAccess
  private lateinit var utils: Utils

  @BeforeEach
  fun setUp() {
    collectionAccess = CollectionAccess()
    utils = Utils()
  }

  @Test
  fun testListWrite() {
    val amount = 1000000
    val values = utils.getRandomStrings(32, amount)
    values.forEach { collectionAccess.writeToList(it) }
    val list = collectionAccess.getList()
    Assertions.assertEquals(amount, list.size)
  }

  @Test
  fun testListRead() {
    val amount = 200000
    var values: List<String>
    val timeToSetup = measureTimeMillis {
      values = utils.getRandomStrings(32, amount)
      values.forEach { collectionAccess.writeToList(it) }
    }
    println(timeToSetup)

    val results = ArrayList<String>()
    val timeToRead = measureTimeMillis {
      val list = collectionAccess.getList()
      for (value in values) {
        val filtered = list.find { it == value }
        if (filtered != null) {
          results.add(filtered)
        }
      }
    }
    println(timeToRead)

    Assertions.assertEquals(amount, results.size)
  }
}
