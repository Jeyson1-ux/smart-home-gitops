package com.example.smarthomegitops.data

import com.example.smarthomegitops.ClosePullRequestRequest
import com.example.smarthomegitops.GithubComment
import com.example.smarthomegitops.PullRequest
import com.example.smarthomegitops.UpdateRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.PUT

interface GitHubApiService {
    @GET("repos/{owner}/{repo}/pulls?state=open")
    suspend fun getOpenPullRequests(
        @Header("Authorization") token: String,
        @Header("User-Agent") userAgent: String = "SmartHomeGitOps",
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<PullRequest>

    @GET("repos/{owner}/{repo}/issues/{pull_number}/comments")
    suspend fun getPullRequestComments(
        @Header("Authorization") token: String,
        @Header("User-Agent") userAgent: String = "SmartHomeGitOps",
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("pull_number") pullNumber: Int
    ): List<GithubComment>

    // Lab2
    @PATCH("repos/{owner}/{repo}/pulls/{pull_number}")
    suspend fun updatePullRequestState(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("pull_number") pullNumber: Int,
        @Body request: ClosePullRequestRequest
    ): Response<PullRequest>

    @PUT("repos/{owner}/{repo}/contents/{path}")
    suspend fun updateFileContent(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String,
        @Body request: UpdateRequest
    ): Response<Void>



    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GitHubApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubApiService::class.java)
        }
    }
}