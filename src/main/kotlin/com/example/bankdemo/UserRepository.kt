package com.example.bankdemo

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.math.BigDecimal

/**
 * This repository contains the database operations for the [User] entity.
 */
@Repository
class UserRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {
    fun getUsers(): List<User> {
        val query = "SELECT * FROM user"

        // The jdbcTemplate.query() method returns a list of results
        return jdbcTemplate.query(query, userRowMapper)
    }

    fun getUserById(id: Int): User? {
        val query = "SELECT * FROM user WHERE id = :id"
        val parameters = mapOf("id" to id)

        // The jdbcTemplate.query() method returns a list of results
        // We use the firstOrNull() extension function to return the first result or null
        return jdbcTemplate.query(query, parameters, userRowMapper).firstOrNull()
    }

    fun insertUser(name: String) {
        val query = "INSERT INTO user(name) VALUES (:name)"
        val parameters = mapOf("name" to name)

        // The jdbcTemplate.update() method returns the number of updated rows
        // If it returns 1, it means that one row was inserted
        jdbcTemplate.update(query, parameters)
    }

    fun updateUser(id: Int, name: String, balance: BigDecimal): Boolean {
        val query = "UPDATE user SET name = :name, balance = :balance WHERE id = :id"
        val parameters = mapOf("id" to id, "name" to name, "balance" to balance)

        // The jdbcTemplate.update() method returns the number of updated rows
        // If it returns 0, it means that no rows were updated
        return jdbcTemplate.update(query, parameters) > 0
    }

    fun deleteUser(id: Int): Boolean {
        val query = "DELETE FROM user WHERE id = :id"
        val parameters = mapOf("id" to id)

        // The jdbcTemplate.update() method returns the number of updated rows
        // If it returns 0, it means that no rows were deleted
        return jdbcTemplate.update(query, parameters) > 0
    }

    companion object {
        /**
         * This converts a database row to a [User] object
         */
        val userRowMapper = RowMapper { resultSet, _ ->
            User(
                id = resultSet.getInt("id"),
                name = resultSet.getString("name"),
                balance = resultSet.getBigDecimal("balance"),
                updatedAt = resultSet.getString("updated_at")
            )
        }
    }
}
