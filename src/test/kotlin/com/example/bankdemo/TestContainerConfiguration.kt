package com.example.bankdemo

import org.flywaydb.core.Flyway
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

/**
 * This class configures the test MySQL container and the test repository.
 */
@Testcontainers
open class TestContainerConfiguration {
    // Test repository that connects to the test database
    lateinit var userRepository: UserRepository

    // Flyway runs the database migrations
    private lateinit var flyway: Flyway

    /**
     * Starts a test MySQL container, runs the Flyway migrations and initializes the test repository.
     * For the database migrations, see the directory: src/main/resources/db/migration
     */
    fun setupMySQLContainerAndRepository() {
        mySQLContainer.start()
        val dataSource = createDataSource()
        configureAndMigrateFlyway(dataSource)
        initializeUserRepository(dataSource)
    }

    private fun createDataSource() = DriverManagerDataSource().apply {
        setDriverClassName(mySQLContainer.driverClassName)
        url = mySQLContainer.jdbcUrl
        username = mySQLContainer.username
        password = mySQLContainer.password
    }

    private fun configureAndMigrateFlyway(dataSource: DriverManagerDataSource) {
        flyway = Flyway.configure()
            .dataSource(dataSource)
            .cleanDisabled(false)
            .load()

        flyway.migrate()
    }

    private fun initializeUserRepository(dataSource: DriverManagerDataSource) {
        val jdbcTemplate = NamedParameterJdbcTemplate(dataSource)
        userRepository = UserRepository(jdbcTemplate)
    }

    /**
     * Resets the database and runs the Flyway migrations.
     */
    fun resetDatabase() {
        flyway.clean()
        flyway.migrate()
    }

    companion object {
        @Container // This creates a test MySQL container
        private val mySQLContainer = MySQLContainer("mysql:8.0.36")
    }
}
