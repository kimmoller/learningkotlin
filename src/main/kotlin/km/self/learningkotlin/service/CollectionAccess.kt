package km.self.learningkotlin.service

import org.springframework.stereotype.Service
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Service
class CollectionAccess {
  val list = ArrayList<String>()
  val map = HashMap<String, String>()

  fun getList(): List<String> {
    return list
  }

  fun getMap(): Map<String, String> {
    return map
  }

  fun readFromList(index: Int): String {
    return list[index]
  }

  fun readFromMap(key: String): String {
    return map[key].orEmpty()
  }

  @Synchronized
  fun writeToList(text: String) {
    list.add(text)
  }

  fun writeToMap(key: String, element: String) {
    map[key] = element
  }

  fun readWriteList(text: String) {
    list.add(text)
    list.forEach { println(it) }
  }

  fun readWriteMap(key: String, text: String) {
    map[key] = text
    map.forEach { println("Map entry: " + it.key + ": " + it.value) }
  }

  fun findFromList(list: List<String>, value: String): String {
    return list.find { it == value }.orEmpty()
  }
}
