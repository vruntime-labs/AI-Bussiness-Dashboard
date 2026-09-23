package com.aibusiness.dashboard.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aibusiness.dashboard.model.*
import com.aibusiness.dashboard.state.UiState

@Composable
fun DashboardScreen(state: UiState, onGenerateClick: () -> Unit, onHistoryClick: () -> Unit, onSettingsClick: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Welcome, ${state.user.name}", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth(), onClick = onGenerateClick) {
            Column(Modifier.padding(16.dp)) {
                Text("New Generation", style = MaterialTheme.typography.titleMedium)
                Text("Create a report, email, summary and more")
            }
        }
        Spacer(Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth(), onClick = onHistoryClick) {
            Column(Modifier.padding(16.dp)) {
                Text("History", style = MaterialTheme.typography.titleMedium)
                Text("${state.history.size} generations so far")
            }
        }
        Spacer(Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth(), onClick = onSettingsClick) {
            Column(Modifier.padding(16.dp)) {
                Text("Settings", style = MaterialTheme.typography.titleMedium)
                Text("API key & preferences")
            }
        }
    }
}

@Composable
fun GenerateScreen(
    state: UiState,
    onPromptChange: (String) -> Unit,
    onFormatSelect: (GenerationFormat) -> Unit,
    onStyleSelect: (GenerationStyle) -> Unit,
    onThemeSelect: (GenerationTheme) -> Unit,
    onGenerate: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Generate Content", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.currentPrompt,
            onValueChange = onPromptChange,
            label = { Text("What do you need?") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
        Spacer(Modifier.height(16.dp))

        Text("Format", style = MaterialTheme.typography.labelLarge)
        DropdownRow(GenerationFormat.entries.map { it.displayName }, state.selectedFormat.displayName) { index ->
            onFormatSelect(GenerationFormat.entries[index])
        }

        Spacer(Modifier.height(12.dp))
        Text("Style", style = MaterialTheme.typography.labelLarge)
        DropdownRow(GenerationStyle.entries.map { it.displayName }, state.selectedStyle.displayName) { index ->
            onStyleSelect(GenerationStyle.entries[index])
        }

        Spacer(Modifier.height(12.dp))
        Text("Tone", style = MaterialTheme.typography.labelLarge)
        DropdownRow(GenerationTheme.entries.map { it.displayName }, state.selectedTheme.displayName) { index ->
            onThemeSelect(GenerationTheme.entries[index])
        }

        Spacer(Modifier.height(24.dp))

        if (state.errorMessage != null) {
            Text(state.errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        Button(onClick = onGenerate, enabled = !state.isLoading, modifier = Modifier.fillMaxWidth()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Generate")
            }
        }
    }
}

@Composable
private fun DropdownRow(options: List<String>, selected: String, onSelect: (Int) -> Unit) {
    var expanded by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(selected)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    onSelect(index)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun ResultScreen(result: GenerationResult?, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Result", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        if (result == null) {
            Text("No result yet. Generate something first.")
        } else {
            Card(Modifier.fillMaxWidth()) {
                Text(result.content, modifier = Modifier.padding(16.dp))
            }
        }
        Spacer(Modifier.height(16.dp))
        OutlinedButton(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun HistoryScreen(history: List<GenerationResult>) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("History", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        if (history.isEmpty()) {
            Text("No generations yet.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(history) { item ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(item.request.format.displayName, style = MaterialTheme.typography.titleSmall)
                            Text(item.request.prompt, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    apiKey: String,
    useRealAI: Boolean,
    isDarkTheme: Boolean,
    onApiKeyChange: (String) -> Unit,
    onUseRealAIChange: (Boolean) -> Unit,
    onToggleDarkTheme: () -> Unit,
    onLogout: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = apiKey,
            onValueChange = onApiKeyChange,
            label = { Text("AI API Key (OpenAI / Claude / compatible)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Switch(checked = useRealAI, onCheckedChange = onUseRealAIChange)
            Spacer(Modifier.width(8.dp))
            Text("Use real AI (requires API key + network wiring)")
        }
        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Switch(checked = isDarkTheme, onCheckedChange = { onToggleDarkTheme() })
            Spacer(Modifier.width(8.dp))
            Text("Dark theme")
        }

        Spacer(Modifier.height(24.dp))
        OutlinedButton(onClick = onLogout) { Text("Log out") }
    }
}
