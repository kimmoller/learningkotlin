package km.self.learningkotlin.dbmigrations

import km.self.learningkotlin.repository.ApplicationRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

@Sql(statements = [
    "insert into application(name) values('testApplication')",
    "insert into application(name) values('secondTestApplication')"
])
class DbReadTest: DbSetup() {
    @Autowired
    lateinit var applicationRepository: ApplicationRepository

    @Test
    fun testReadData() {
        val applications = applicationRepository.findAll()
        assertEquals(2, applications.size)

        assertEquals(1, applications[0].id)
        assertEquals("testApplication", applications[0].name)

        assertEquals(2, applications[1].id)
        assertEquals("secondTestApplication", applications[1].name)
    }

    @Test
    @Disabled("Assertion is broken")
    fun testShouldBeDisabled() {
        val applications = applicationRepository.findAll()
        assertEquals(1, applications.size)
    }
}