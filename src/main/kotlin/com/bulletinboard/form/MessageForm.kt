package com.bulletinboard.form

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class MessageForm(
    @NotBlank
    @Size(max = 15)
    val name: String = "",
    @NotBlank
    @Size(max = 20)
    val title: String = "",
    @NotBlank
    @Size(max = 400)
    val message: String = "",
)
