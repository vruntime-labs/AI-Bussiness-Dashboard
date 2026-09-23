package com.aibusiness.dashboard.state

import com.aibusiness.dashboard.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val isLoggedIn: Boolean = false,
    val user: User = User(),
    val isLoading: Boolean = false,
    val currentPrompt: String = "",
    val selectedFormat: GenerationFormat = GenerationFormat.REPORT,
    val selectedStyle: GenerationStyle = GenerationStyle.PROFESSIONAL,
    val selectedTheme: GenerationTheme = GenerationTheme.BUSINESS,
    val apiKey: String = "",
    val useRealAI: Boolean = false,
    val latestResult: GenerationResult? = null,
    val history: List<GenerationResult> = emptyList(),
    val errorMessage: String? = null,
    val isDarkTheme: Boolean = false
)

/**
 * Holds all app state. Works identically on Android, Windows, Mac and Linux.
 * History is in-memory for now (resets on restart) — swap in a real file/DB
 * store later without touching the UI.
 */
class AppState(private val scope: CoroutineScope) {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun login(name: String = "Business User", email: String = "user@company.com") {
        _uiState.update { it.copy(isLoggedIn = true, user = User(name, email, true)) }
    }

    fun logout() {
        _uiState.update { it.copy(isLoggedIn = false, user = User()) }
    }

    fun toggleDarkTheme() {
        _uiState.update { it.copy(isDarkTheme = !it.isDarkTheme) }
    }

    fun updatePrompt(text: String) {
        _uiState.update { it.copy(currentPrompt = text) }
    }

    fun selectFormat(format: GenerationFormat) = _uiState.update { it.copy(selectedFormat = format) }
    fun selectStyle(style: GenerationStyle) = _uiState.update { it.copy(selectedStyle = style) }
    fun selectTheme(theme: GenerationTheme) = _uiState.update { it.copy(selectedTheme = theme) }

    fun updateApiKey(key: String) = _uiState.update { it.copy(apiKey = key) }
    fun setUseRealAI(value: Boolean) = _uiState.update { it.copy(useRealAI = value) }

    fun generate() {
        val state = _uiState.value
        if (state.currentPrompt.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Please enter a prompt first") }
            return
        }
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        scope.launch {
            delay(1200) // simulate generation time

            val request = GenerationRequest(
                prompt = state.currentPrompt,
                format = state.selectedFormat,
                style = state.selectedStyle,
                theme = state.selectedTheme
            )

            // Placeholder content. Wire this up to a real AI API call
            // (e.g. Ktor client -> OpenAI/Claude) using state.apiKey when ready.
            val content = buildString {
                append("[${state.selectedFormat.displayName}] ")
                append("(${state.selectedStyle.displayName} · ${state.selectedTheme.displayName})\n\n")
                append("Based on your prompt: \"${state.currentPrompt}\"\n\n")
                append("This is placeholder AI content. Connect a real API key in Settings ")
                append("and wire up the network call to replace this text.")
            }

            val result = GenerationResult(request = request, content = content)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    latestResult = result,
                    history = listOf(result) + it.history
                )
            }
        }
    }

    fun clearError() = _uiState.update { it.copy(errorMessage = null) }
}
