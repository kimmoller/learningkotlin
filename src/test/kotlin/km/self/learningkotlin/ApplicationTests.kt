package km.self.learningkotlin

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Disabled("No local db")
class ApplicationTests {
  @Test fun contextLoads() {}
}
