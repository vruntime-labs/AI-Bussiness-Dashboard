package com.aibusiness.dashboard

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "AI Business Dashboard") {
        App()
    }
}
