package com.example.patitapp.backend.service

import com.example.patitapp.backend.model.User
import com.example.patitapp.backend.repository.UserRepository
import org.springframework.stereotype.Service

//métodos para llamar a la base de datos
@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun createUser(user: User): User {
        return userRepository.save(user)
    }
}
