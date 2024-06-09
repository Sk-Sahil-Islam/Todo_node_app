package com.example.todonode.presentation.login_screen.componants

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: ButtonColors,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier
            .requiredWidth(200.dp),
        onClick = onClick,
        colors = colors,
        content = content
    )
}