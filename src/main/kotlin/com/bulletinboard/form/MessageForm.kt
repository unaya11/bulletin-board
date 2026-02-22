package com.bulletinboard.form

import jakarta.validation.constraints.NotBlank

data class MessageForm(
    @NotBlank(message = "名前を入力してください")
    val name: String = "",
    @NotBlank(message = "タイトルを入力してください")
    val title: String = "",
    @NotBlank(message = "メッセージを入力してください")
    val message: String = "",
)
