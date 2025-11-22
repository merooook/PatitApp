package com.example.patitapp.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

//llamada random, aquí el matías tiene que hacer GET, POST, etc a la API
    @GetMapping("/GET/x")
    fun cualquierGet(): String {
        return "GET x"
    }
}
