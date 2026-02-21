package com.bulletinboard.form

import jakarta.validation.constraints.NotBlank

data class PostForm(
    @NotBlank
    val name: String,
    @NotBlank
    val title: String,
    @NotBlank
    val message: String,
)
