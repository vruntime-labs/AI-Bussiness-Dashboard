package com.aibusiness.dashboard.model

import kotlin.random.Random

expect fun currentTimeMillis(): Long

fun newId(): String = "${currentTimeMillis()}-${Random.nextInt(100000, 999999)}"

enum class GenerationFormat(val displayName: String) {
    REPORT("Professional Report"),
    EMAIL("Business Email"),
    SUMMARY("Executive Summary"),
    PRESENTATION("Presentation Outline"),
    BLOG("Blog / Article"),
    LETTER("Formal Letter"),
    PROPOSAL("Business Proposal"),
    ANALYSIS("Data Analysis"),
    SOCIAL("Social Media Post"),
    CUSTOM("Custom Format")
}

enum class GenerationStyle(val displayName: String) {
    PROFESSIONAL("Professional"),
    CONCISE("Concise & Direct"),
    DETAILED("Detailed & Thorough"),
    EXECUTIVE("Executive Level"),
    CASUAL("Casual Professional"),
    TECHNICAL("Technical"),
    PERSUASIVE("Persuasive"),
    ANALYTICAL("Analytical")
}

enum class GenerationTheme(val displayName: String) {
    BUSINESS("Business"),
    FORMAL("Formal"),
    CREATIVE("Creative"),
    ANALYTICAL("Analytical"),
    INSPIRATIONAL("Inspirational"),
    NEUTRAL("Neutral"),
    AUTHORITATIVE("Authoritative")
}

data class GenerationRequest(
    val prompt: String,
    val format: GenerationFormat = GenerationFormat.REPORT,
    val style: GenerationStyle = GenerationStyle.PROFESSIONAL,
    val theme: GenerationTheme = GenerationTheme.BUSINESS
)

data class GenerationResult(
    val id: String = newId(),
    val request: GenerationRequest,
    val content: String,
    val timestamp: Long = currentTimeMillis(),
    val isSuccess: Boolean = true,
    val errorMessage: String? = null
)

data class User(
    val name: String = "Business User",
    val email: String = "user@company.com",
    val isLoggedIn: Boolean = false
)

data class DashboardCard(
    val title: String,
    val subtitle: String,
    val route: String
)
