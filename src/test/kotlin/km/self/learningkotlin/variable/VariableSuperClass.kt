package km.self.learningkotlin.variable

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

open class VariableSuperClass {
    companion object {
        var name = "Companion object"

        @BeforeAll
        @JvmStatic
        fun superSetUp() {
            println("Super setup " + name)
        }

        @AfterAll
        @JvmStatic
        fun superTearDown() {
            println("Super teardown " + name)
        }
    }

    fun execute(text: String) {
        name = this.javaClass.simpleName + " " + text
        println(name)
    }
}