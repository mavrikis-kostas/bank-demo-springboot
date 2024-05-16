package com.example.bankdemo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var userRepository: UserRepository

    /**
     * This test verifies that the /users endpoint returns the correct response.
     *
     * How to create a test:
     * First, we need to mock the UserRepository.getUsers() method
     * Then, we need to call the /users endpoint
     * Finally, we need to assert that the response is correct
     */
    @Test
    fun `GET users should return all users`() {
        // Create a list of users
        val expectedUsers = listOf(
            User(
                id = 1,
                name = "Alice",
                balance = BigDecimal("1000.00"),
                updatedAt = "2024-01-01T00:00:00Z"
            ),
            User(
                id = 2,
                name = "Bob",
                balance = BigDecimal("2000.00"),
                updatedAt = "2024-01-02T00:00:00Z"
            )
        )

        // Mock the getUsers() method to return the expectedUsers list
        every { userRepository.getUsers() } returns expectedUsers

        // Convert the expectedUsers list to JSON
        val expectedUsersJson = objectMapper.writeValueAsString(expectedUsers)

        // Call the /users endpoint
        mockMvc.get("/users")
            .andExpect { status().isOk }
            .andExpect { content().json(expectedUsersJson) }
    }

    /**
     * This test verifies that the /users/{id} endpoint returns the correct response.
     */
    @Test
    fun `GET users by id should return the correct user`() {
        val expectedUser = User(
            id = 1,
            name = "Alice",
            balance = BigDecimal("1000.00"),
            updatedAt = "2024-01-01T00:00:00Z"
        )

        every { userRepository.getUserById(1) } returns expectedUser

        val expectedUserJson = objectMapper.writeValueAsString(expectedUser)
        mockMvc.get("/users/1")
            .andExpect { status().isOk }
            .andExpect { content().json(expectedUserJson) }    }

    companion object {
        // This converts Kotlin objects to JSON
        val objectMapper = jacksonObjectMapper()
    }
}