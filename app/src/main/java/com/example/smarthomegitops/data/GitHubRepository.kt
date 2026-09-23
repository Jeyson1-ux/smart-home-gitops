package com.example.smarthomegitops.data

import com.example.smarthomegitops.BuildConfig
import com.example.smarthomegitops.ClosePullRequestRequest

class GitHubRepository (
    private val apiService: GitHubApiService = GitHubApiService.create()
) {
    private val authHeader = "Bearer ${BuildConfig.GITHUB_TOKEN}"
    private val owner = "Jeyson1-ux"
    private val repoName = "smart-home-gitops"

    suspend fun fetchLatestAttackComment(): String? {
        return try {
            val prs = apiService.getOpenPullRequests(
                token = authHeader,
                owner = owner,
                repo = repoName
            )
            val latestPr = prs.firstOrNull() ?: return null

            val comments = apiService.getPullRequestComments(
                token = authHeader,
                owner = owner,
                repo = repoName,
                pullNumber = latestPr.number
            )

            comments.lastOrNull()?.body
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    suspend fun forceRejectPullRequest(pullNumber: Int): Boolean {
        val request = ClosePullRequestRequest(state = "closed")
        val response = apiService.updatePullRequestState(
            token = authHeader,
            owner = owner,
            repo = repoName,
            pullNumber =pullNumber,
            request = request
        )
        return response.isSuccessful
    }

    suspend fun forceMergeProposal(pullNumber: Int): Boolean {
        val request = ClosePullRequestRequest(state = "closed")
        val response = apiService.updatePullRequestState(
            token = authHeader,
            owner = owner,
            repo = repoName,
            pullNumber =pullNumber,
            request = request
        )
        return response.isSuccessful
    }
}