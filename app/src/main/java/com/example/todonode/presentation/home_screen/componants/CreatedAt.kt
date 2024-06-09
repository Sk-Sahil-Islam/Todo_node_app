package com.example.todonode.presentation.home_screen.componants

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreatedAt(
    modifier: Modifier = Modifier,
    createdAt: LocalDateTime
) {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")
    val formattedDateTime = createdAt.format(formatter)
    Box(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Outlined.DateRange,
                contentDescription = "created at"
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = formattedDateTime,
                fontSize = 13.sp
            )
        }
    }
}