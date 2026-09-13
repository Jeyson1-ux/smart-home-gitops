package com.example.smarthomegitops.domain

data class DetectionResult(
    val isAttack: Boolean,
    val confidenceScore: Int,
    val matchedText: String
)

class DeceptionDetector {
    private var adversialPatterns = listOf(
        "freezing",
        "failure",
        "compression blowout",
        "critical",
        "electrical issue",
        "structural crack",
        "urgent",
        "do not lower"
    )

    fun analyseComment(commentBody: String?): DetectionResult {
        if (commentBody.isNullOrBlank()) {
            return DetectionResult(isAttack = false, confidenceScore = 0, matchedText = "")
        }

        val lowerCaseBody = commentBody.lowercase()
        var matchCount = 0

        for (pattern in adversialPatterns) {
            if (lowerCaseBody.contains(pattern)) {
                matchCount++
            }
        }

        val confidence = if (matchCount > 0) {
            val score = matchCount * 35
            if (score > 100) 100 else score
        } else {
            0
        }

        return DetectionResult(
            isAttack = confidence > 0,
            confidenceScore =  confidence,
            matchedText = commentBody
        )
    }
}