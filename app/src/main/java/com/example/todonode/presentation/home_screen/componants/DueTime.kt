package com.example.todonode.presentation.home_screen.componants

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todonode.R
import com.example.todonode.ui.theme.MyGray
import com.example.todonode.ui.theme.MyGreen
import com.example.todonode.ui.theme.MyRed
import com.example.todonode.ui.theme.MyYellow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DueTime(
    modifier: Modifier = Modifier,
    dueTime: LocalDateTime,
    currentDateTime: LocalDateTime
) {
    val daysDifference = ChronoUnit.DAYS.between(currentDateTime, dueTime).toInt()

    val dueTimeText = when {
        currentDateTime.isAfter(dueTime) -> "Overdue"
        else -> dueTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }

    Log.e("DueTime", "DueTime: $daysDifference")

    val backgroundColor = when {
        currentDateTime.isAfter(dueTime) -> MyGray
        daysDifference <= 1 -> MyRed
        daysDifference == 2 -> MyYellow
        else -> MyGreen
    }
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(100))
            .background(backgroundColor)
            .padding(vertical = 5.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_schedule),
                contentDescription = "Due Time",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = dueTimeText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}