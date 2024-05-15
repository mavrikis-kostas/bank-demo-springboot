package com.example.bankdemo

import org.springframework.stereotype.Service

/**
 * This service contains the business logic for the [User] entity.
 * @see UserRepository
 */
@Service
class UserService(val userRepository: UserRepository) {
    fun getUsers(): List<User> {
        return userRepository.getUsers()
    }

    fun getUserById(id: Int): User? {
        return userRepository.getUserById(id)
    }

    fun addUser(createUserRequest: CreateUserRequest) {
        userRepository.insertUser(createUserRequest.name)
    }

    fun updateUser(id: Int, updateUserRequest: UpdateUserRequest): Boolean {
        return userRepository.updateUser(id, updateUserRequest.name, updateUserRequest.balance)
    }

    fun deleteUser(id: Int): Boolean {
        return userRepository.deleteUser(id)
    }
}
