package com.example.smarthomegitops

import com.google.gson.annotations.SerializedName

data class PullRequest (
    val number: Int,
    val title: String,
    val state: String,
    @SerializedName("comments_url") val commentsUrl: String
)

data class GithubComment(
    val id: Long,
    val body: String,
    @SerializedName("user") val user: GithubUser
)

data class GithubUser(
    val login: String
)
