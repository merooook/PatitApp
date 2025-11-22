package com.example.patitapp.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PatitAppApplication

fun main(args: Array<String>) {
    runApplication<PatitAppApplication>(*args)
}
