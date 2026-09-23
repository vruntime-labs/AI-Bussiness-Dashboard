package com.aibusiness.dashboard.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("AI Business Dashboard", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text("Sign in to continue (demo mode)", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(32.dp))
            Button(onClick = onLogin, modifier = Modifier.fillMaxWidth(0.6f)) {
                Text("Continue as Business User")
            }
        }
    }
}
