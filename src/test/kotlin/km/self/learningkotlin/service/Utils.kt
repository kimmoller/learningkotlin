package km.self.learningkotlin.service

class Utils {
  fun getRandomStrings(strLength: Int, amount: Int): List<String> {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val strings = ArrayList<String>()

    for (i in 1..amount) {
      val string = List(strLength) { charPool.random() }.joinToString("")
      strings.add(string)
    }

    return strings
  }
}
