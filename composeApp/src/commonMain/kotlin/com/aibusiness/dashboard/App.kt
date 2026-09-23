package com.aibusiness.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.aibusiness.dashboard.state.AppState
import com.aibusiness.dashboard.ui.screens.*
import com.aibusiness.dashboard.ui.theme.DashboardTheme

private enum class Screen { LOGIN, DASHBOARD, GENERATE, RESULT, HISTORY, SETTINGS }

@Composable
fun App() {
    val scope = rememberCoroutineScope()
    val appState = remember { AppState(scope) }
    val state by appState.uiState.collectAsState()

    var screen by remember { mutableStateOf(Screen.LOGIN) }

    DashboardTheme(darkTheme = state.isDarkTheme) {
        Surface(modifier = Modifier.fillMaxSize()) {
            when {
                !state.isLoggedIn -> LoginScreen(onLogin = {
                    appState.login()
                    screen = Screen.DASHBOARD
                })

                else -> when (screen) {
                    Screen.DASHBOARD -> DashboardScreen(
                        state = state,
                        onGenerateClick = { screen = Screen.GENERATE },
                        onHistoryClick = { screen = Screen.HISTORY },
                        onSettingsClick = { screen = Screen.SETTINGS }
                    )

                    Screen.GENERATE -> GenerateScreen(
                        state = state,
                        onPromptChange = appState::updatePrompt,
                        onFormatSelect = appState::selectFormat,
                        onStyleSelect = appState::selectStyle,
                        onThemeSelect = appState::selectTheme,
                        onGenerate = {
                            appState.generate()
                            screen = Screen.RESULT
                        }
                    )

                    Screen.RESULT -> ResultScreen(
                        result = state.latestResult,
                        onBack = { screen = Screen.DASHBOARD }
                    )

                    Screen.HISTORY -> HistoryScreen(history = state.history)

                    Screen.SETTINGS -> SettingsScreen(
                        apiKey = state.apiKey,
                        useRealAI = state.useRealAI,
                        isDarkTheme = state.isDarkTheme,
                        onApiKeyChange = appState::updateApiKey,
                        onUseRealAIChange = appState::setUseRealAI,
                        onToggleDarkTheme = appState::toggleDarkTheme,
                        onLogout = {
                            appState.logout()
                            screen = Screen.DASHBOARD
                        }
                    )

                    Screen.LOGIN -> Unit
                }
            }
        }
    }
}
