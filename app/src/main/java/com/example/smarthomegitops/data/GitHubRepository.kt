package com.example.smarthomegitops.data

import com.example.smarthomegitops.BuildConfig

class GitHubRepository (
    private val apiService: GitHubApiService = GitHubApiService.create()
) {
    private val authHeader = "Bearer ${BuildConfig.GITHUB_TOKEN}"
    private val owner = "Jeyson1-ux"
    private val repoName = "smart-home-gitops"

    suspend fun fetchLatestAttackComment(): String? {
        return try {
            val prs = apiService.getOpenPullRequests(authHeader, owner, repoName)
            val latestPr = prs.firstOrNull() ?: return null

            val comments = apiService.getPullRequestComments(authHeader, owner, repoName, latestPr.number)

            comments.lastOrNull()?.body
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}