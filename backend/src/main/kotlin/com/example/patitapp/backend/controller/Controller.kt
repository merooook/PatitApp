package com.example.patitapp.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @GetMapping("/GET/home")
    fun home(): String {
        return "Cualquier get"
    }
}
