package com.bulletinboard.form

import jakarta.validation.constraints.NotBlank

data class ReplyForm(
    @NotBlank
    val name: String,
    @NotBlank
    val reply: String,
)
