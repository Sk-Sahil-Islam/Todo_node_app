package com.example.todonode.presentation.update_todo_screen

import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.todonode.R
import com.example.todonode.TestUpdateViewModel
import com.example.todonode.data.remote.dto.TodoRequest
import com.example.todonode.presentation.Screen
import com.example.todonode.presentation.add_todo_screen.componants.CustomTextField
import com.example.todonode.presentation.add_todo_screen.componants.TimePickerDialog
import com.example.todonode.ui.theme.BackgroundDark
import com.example.todonode.ui.theme.LoginPrimaryDark
import com.example.todonode.ui.theme.MyGray
import com.example.todonode.ui.theme.MyGreen
import com.example.todonode.ui.theme.MyRed
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.UpdateTodoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    todoId: String,
    todoTitle: String,
    todoDescription: String,
    todoDeadline: String,
    isFinished: Boolean,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: TestUpdateViewModel = hiltViewModel()
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val state by viewModel.updateState.collectAsState()

    var task by remember { mutableStateOf(todoTitle) }
    var description by remember { mutableStateOf(todoDescription) }

    val dateFormatterWithTime = SimpleDateFormat("dd MM yyyy HH:mm", Locale.getDefault())

    val initialDateInMillis =
        LocalDateTime.parse(todoDeadline).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    val datePickerState = rememberDatePickerState(initialDateInMillis)
    var showDatePicker by remember { mutableStateOf(false) }

    var initialFinished by remember { mutableStateOf(isFinished) }

    var initialHour = LocalDateTime.parse(todoDeadline).hour
    var initialMinute = LocalDateTime.parse(todoDeadline).minute

    var showTimePicker by remember { mutableStateOf(false) }

    var combinedDate by remember {
        mutableStateOf(
            Calendar.getInstance().apply {
                timeInMillis = initialDateInMillis
            }.time
        )
    }

    val isDeleted by viewModel.isDeleted.collectAsState()

    LaunchedEffect(state.error) {
        if (state.error.isNotBlank())
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }
    LaunchedEffect(state.response?.success) {
        if (state.response?.success == true) {
            Toast.makeText(context, state.response!!.message, Toast.LENGTH_SHORT).show()

            val currentState = lifeCycleOwner.lifecycle.currentState
            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(0)
                }
            }
        }
    }

    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT).show()

            val currentState = lifeCycleOwner.lifecycle.currentState
            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(0)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Update Todo")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {

                        viewModel.updateTodo(
                            id = todoId,
                            todo = TodoRequest(
                                title = task,
                                description = description,
                                deadline = combinedDate,
                                finished = initialFinished
                            )
                        )
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.ic_update),
                            contentDescription = "update",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = {
                        viewModel.deleteTodo(
                            todoId
                        )
                    },
                    containerColor = MyRed
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete",
                        tint = BackgroundDark
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))

                ExtendedFloatingActionButton(
                    onClick = {
                        initialFinished = !initialFinished
                    },
                    icon = {
                        if (initialFinished) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_exclamation),
                                contentDescription = "unfinished",
                                tint = BackgroundDark
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_task_alt),
                                contentDescription = "completed",
                                tint = BackgroundDark
                            )
                        }
                    },
                    text = {
                        if (initialFinished) {
                            Text("Unfinished", color = BackgroundDark, fontWeight = FontWeight.SemiBold)
                        } else {
                            Text("Finished", color = BackgroundDark, fontWeight = FontWeight.SemiBold)
                        }
                    },
                    containerColor = if(initialFinished) MyGray else LoginPrimaryDark
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Deadline",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = dateFormatterWithTime.format(combinedDate),
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                ) {

                    OutlinedButton(
                        modifier = Modifier.widthIn(min = 100.dp),
                        onClick = {
                            showDatePicker = true
                        }
                    ) {
                        Text("Change date")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedButton(
                        modifier = Modifier.widthIn(min = 100.dp),
                        onClick = {
                            showTimePicker = true
                        }
                    ) {
                        Text("Time")
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                }

                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            state = rememberSharedContentState(key = "title/${todoId}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(1000)
                            }
                        ),
                    value = task,
                    onValueChange = {
                        task = it
                    },
                    placeholder = {
                        Text("Task", fontSize = 22.sp)
                    },
                    fontSize = 22.sp,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            state = rememberSharedContentState(key = "description/${todoId}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(1000)
                            }
                        ),
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    placeholder = {
                        Text("Description", fontSize = 18.sp)
                    },
                    fontSize = 18.sp,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            if (showDatePicker) {

                val timePickerState = rememberTimePickerState(
                    initialHour = initialHour,
                    initialMinute = initialMinute,
                    is24Hour = true
                )

                DatePickerDialog(
                    onDismissRequest = {
                        showDatePicker = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val selectedDateTime = Calendar.getInstance()
                                    .apply { timeInMillis = datePickerState.selectedDateMillis!! }

                                selectedDateTime.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                selectedDateTime.set(Calendar.MINUTE, timePickerState.minute)

                                if (selectedDateTime.after(Calendar.getInstance().time)) {
                                    combinedDate = selectedDateTime.time

                                    showDatePicker = false
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Selected time should be after current time, please select again",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = { }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            if (showTimePicker) {

                val timePickerState = rememberTimePickerState(
                    initialHour = initialHour,
                    initialMinute = initialMinute,
                    is24Hour = true
                )

                TimePickerDialog(
                    onDismissRequest = {
                        showTimePicker = false

                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val selectedDateTime = Calendar.getInstance()
                                    .apply { timeInMillis = datePickerState.selectedDateMillis!! }

                                selectedDateTime.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                selectedDateTime.set(Calendar.MINUTE, timePickerState.minute)

                                if (selectedDateTime.after(Calendar.getInstance().time)) {
                                    combinedDate = selectedDateTime.time
                                    initialHour = timePickerState.hour
                                    initialMinute = timePickerState.minute

                                    showTimePicker = false
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Selected time should be after current time, please select again",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = { }
                )
                {
                    TimePicker(state = timePickerState)
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}

