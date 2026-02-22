package com.bulletinboard.form

import jakarta.validation.constraints.NotBlank

data class ReplyForm(
    @NotBlank(message = "名前を入力してください")
    val name: String = "",
    @NotBlank(message = "メッセージを入力してください")
    val reply: String = "",
)
