package km.self.learningkotlin.dbmigrations

import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DbSetup {
    companion object {
        private lateinit var flyway: Flyway
        // Start the container during init phase to use the same container
        // for all test classes that inherit the setup class
        // Migrate and clean the db in between all test classes for a clean start
        // Could also migrate/clean between all test cases if needed but that will slow down execution.
        private val db = PostgreSQLContainer("postgres").apply {
            println("Starting container...")
            start()
        }

        @BeforeAll
        @JvmStatic
        fun dbSetup() {
            println("Setting up database migration")
            flyway = Flyway
                .configure()
                .dataSource(db.jdbcUrl, db.username, db.password)
                .cleanDisabled(false)
                .load()
            flyway.migrate()
        }

        @AfterAll
        @JvmStatic
        fun dbTearDown() {
            println("Cleaning database")
            flyway.clean()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerDBContainer(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", db::getJdbcUrl)
            registry.add("spring.datasource.username", db::getUsername)
            registry.add("spring.datasource.password", db::getPassword)
        }
    }
}