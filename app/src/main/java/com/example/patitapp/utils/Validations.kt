package com.example.patitapp.utils


object Validations {
    // Email simple y compatible
    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    fun esEmail(valor: String) = EMAIL_REGEX.matches(valor)

    // lago minimo 8 caracteres  --> al menos 1 letra y 1 número
    private val PASS_REGEX = Regex("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")
    fun esPasswordFuerte(valor: String) = PASS_REGEX.matches(valor)
}
