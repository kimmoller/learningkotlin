package km.self.learningkotlin.dbmigrations

import km.self.learningkotlin.entity.ApplicationEntity
import km.self.learningkotlin.repository.ApplicationRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

@Sql(statements = [
    "insert into application(name) values('writeTestApplication')",
    "insert into application(name) values('secondWriteTestApplication')"
])
class DbWriteTest: DbSetup() {
    @Autowired
    lateinit var applicationRepository: ApplicationRepository

    @Test
    fun writeToDb() {
        val all = applicationRepository.findAll()
        assertEquals(2, all.size)

        val applicationEntity = ApplicationEntity(null, "Written Application")
        applicationRepository.save(applicationEntity)

        val application = applicationRepository.findById(3).orElseThrow()
        assertEquals(3, application.id)
        assertEquals("Written Application", application.name)
    }
}