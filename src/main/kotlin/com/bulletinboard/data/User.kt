package com.bulletinboard.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
data class User(
    // 主キーは自動採番されるのでnull許容（?）としている。
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Int?,
    // リフレクションでオブジェクトを作る際、nullを入れるのでnullable = false としている場合エラーで落ちることがある。
    // 対処法はnull許容（?）とするか初期値を入れておく。null許容とすると毎回のnullチェックが必要となるので、初期値を入れておく方がスマート。
    @Column(length = 100, nullable = false)
    val name: String = "",
    @Column(length = 255, nullable = false)
    val email: String = "",
//    @OneToMany(mappedBy = "message")
//    val postContent: MutableList<Message> = mutableListOf(),
)
