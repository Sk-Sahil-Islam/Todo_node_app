package com.example.todonode.presentation.home_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todonode.ui.theme.MyGray
import com.example.todonode.ui.theme.MyGreen

@Composable
fun IsDone(
    modifier: Modifier = Modifier,
    isDone: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isDone) MyGreen else MyGray.copy(alpha = 0.5f)
    Box {
        if (!isDone) {

            Box(
                modifier = modifier
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .clickable { onClick() }
                    .size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Done,
                    contentDescription = "Done",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        } else {
            Box(
                modifier = modifier
                    .clip(shape = RoundedCornerShape(100))
                    .background(backgroundColor)
                    .padding(vertical = 5.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Completed",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}