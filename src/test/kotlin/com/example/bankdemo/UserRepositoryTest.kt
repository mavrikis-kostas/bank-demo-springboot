package com.example.bankdemo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigDecimal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest : TestContainerConfiguration() {
    @BeforeAll
    fun setUp() {
        // Before each test, set up the test MySQL container and the test repository
        setupMySQLContainerAndRepository()
    }

    @AfterEach
    fun reset() {
        // After each test, reset the database
        resetDatabase()
    }

    /**
     * The database is initialized with data from: src/main/resources/db/migration/V1.1.0__insert_data.sql
     *
     * INSERT INTO user(`id`, `name`, `balance`, `updated_at`)
     * VALUES (1, 'Superman', 1000, '2024-03-01 00:00:00'),
     *        (2, 'Batman', 2000, '2024-03-01 00:00:00'),
     *        (3, 'Spider-man', 3000, '2024-03-01 00:00:00');
     */
    @Test
    fun `getUsers should return all users in the database`() {
        val actualUsers = userRepository.getUsers()
        val expectedUsers = listOf(
            User(
                id = 1,
                name = "Superman",
                balance = BigDecimal("1000.00"),
                updatedAt = "2024-03-01 00:00:00"
            ),
            User(
                id = 2,
                name = "Batman",
                balance = BigDecimal("2000.00"),
                updatedAt = "2024-03-01 00:00:00"
            ),
            User(
                id = 3,
                name = "Spider-man",
                balance = BigDecimal("3000.00"),
                updatedAt = "2024-03-01 00:00:00"
            ),
        )

        assertThat(actualUsers).containsExactlyInAnyOrderElementsOf(expectedUsers)
    }

    /**
     * The database is initialized with data from: src/main/resources/db/migration/V1.1.0__insert_data.sql
     *
     * INSERT INTO user(`id`, `name`, `balance`, `updated_at`)
     * VALUES (1, 'Superman', 1000, '2024-03-01 00:00:00'),
     *        (2, 'Batman', 2000, '2024-03-01 00:00:00'),
     *        (3, 'Spider-man', 3000, '2024-03-01 00:00:00');
     */
    @Test
    fun `getUserById should return the user with the given id`() {
        val actualUser = userRepository.getUserById(1)
        val expectedUser = User(
            id = 1,
            name = "Superman",
            balance = BigDecimal("1000.00"),
            updatedAt = "2024-03-01 00:00:00"
        )

        assertThat(actualUser).isEqualTo(expectedUser)
    }

    @Test
    fun `insertUser should insert a new user into the database`() {
        val name = "Superwoman"
        userRepository.insertUser(name)

        val actualUsers = userRepository.getUsers()
        assertThat(actualUsers).anyMatch { it.name == name && it.balance == BigDecimal("0.00") }
    }

    @Test
    fun `updateUser should update the user with the given id`() {
        val id = 1
        val name = "Clark Kent"
        val balance = BigDecimal("5000.00")
        userRepository.updateUser(id, name, balance)

        val actualUser = userRepository.getUserById(id)!!
        assertThat(actualUser).matches { it.name == name && it.balance == balance }
    }

    @Test
    fun deleteUser() {
        val id = 1
        userRepository.deleteUser(id)

        val actualUser = userRepository.getUserById(id)
        assertThat(actualUser).isNull()
    }
}