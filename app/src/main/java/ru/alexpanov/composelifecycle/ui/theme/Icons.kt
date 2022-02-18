package ru.alexpanov.composelifecycle.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector

data class CustomIcons(
    val user: ImageVector
)

val LocalIcons = staticCompositionLocalOf { LightIcons }

val DarkIcons = CustomIcons(
    user = Icons.Outlined.Person
)
val LightIcons = CustomIcons(
    user = Icons.Filled.Person
)