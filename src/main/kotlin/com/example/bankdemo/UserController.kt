package com.example.bankdemo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

/**
 * This is the controller for the [User] entity.
 * @see UserService
 */
@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    @GetMapping
    fun getUsers(): List<User> {
        return userService.getUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): User? {
        // If the user is null, we return HTTP 404 Not Found
        return userService.getUserById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PostMapping
    fun addUser(@RequestBody createUserRequest: CreateUserRequest) {
        userService.addUser(createUserRequest)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Int, @RequestBody updateUserRequest: UpdateUserRequest) {
        val userUpdated = userService.updateUser(id, updateUserRequest)

        // If no rows were updated, we return HTTP 404 Not Found
        if (!userUpdated) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int) {
        val userDeleted = userService.deleteUser(id)

        // If no rows were deleted, we return HTTP 404 Not Found
        if (!userDeleted) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }
}
