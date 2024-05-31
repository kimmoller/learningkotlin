package km.self.learningkotlin.variable

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class VariableBehaviourTest: VariableSuperClass() {
    @BeforeEach
    fun setUp() {
        execute("before")
    }

    @AfterEach
    fun tearDown() {
        execute("after")
    }

    @Test
    fun testCase() {
        execute("test case")
    }
}