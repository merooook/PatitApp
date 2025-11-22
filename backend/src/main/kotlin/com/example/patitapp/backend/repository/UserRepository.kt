package com.example.patitapp.backend.repository

import com.example.patitapp.backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

//repositorio con JPA para la entidad User, CRUD.
@Repository
interface UserRepository : JpaRepository<User, Long>
