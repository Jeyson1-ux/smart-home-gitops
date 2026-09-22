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

// LAB 2
// Payload model used when sending a PATCH request to close a pull request (Force Reject).
data class ClosePullRequestRequest(
    val state: String = "closed"
)

// Payload model used when sending a PUT request to update/overwrite a file on GitHub (Force Merge).
data class UpdateRequest(
    @SerializedName("message") val message: String,
    @SerializedName("content") val content: String,
    @SerializedName("sha") val sha: String
)