package com.example.todonode.presentation.add_todo_screen

import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.todonode.TestAddViewModel
import com.example.todonode.data.remote.dto.TodoRequest
import com.example.todonode.presentation.Screen
import com.example.todonode.presentation.add_todo_screen.componants.CustomTextField
import com.example.todonode.presentation.add_todo_screen.componants.TimePickerDialog
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TestAddViewModel = hiltViewModel()
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val state by viewModel.addTodoState.collectAsState()

    var task by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val dateFormatter = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
    val dateFormatterWithTime = SimpleDateFormat("dd MM yyyy HH:mm", Locale.getDefault())

    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = 23,
        initialMinute = 59,
        is24Hour = true
    )
    var showTimePicker by remember { mutableStateOf(false) }

    var combinedDate by remember {
        mutableStateOf(
            Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
            }.time
        )
    }


    LaunchedEffect(state.error) {
        if(state.error.isNotBlank())
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }
    LaunchedEffect(state.response?.success) {
        if (state.response?.success == true) {
            Toast.makeText(context, state.response!!.msg, Toast.LENGTH_SHORT).show()

            val currentState = lifeCycleOwner.lifecycle.currentState
            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                navController.navigate(Screen.HomeScreen.route){
                    popUpTo(0)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Todo")
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
//                        val instant = combinedDate.toInstant()
//                        val zoneId = ZoneId.systemDefault()
//                        val localDateTime = LocalDateTime.ofInstant(instant, zoneId)
//                        Log.e("LocalDateTime", localDateTime.toString())

                        viewModel.addTodo(
                            todo = TodoRequest(
                                title = task,
                                description = description,
                                deadline = combinedDate
                            )
                        )
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Rounded.Done,
                            contentDescription = "Add",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
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
                    modifier = Modifier.fillMaxWidth(),
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
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        disabledIndicatorColor = LocalContentColor.current.copy(alpha = 0.2f),
                        unfocusedIndicatorColor = LocalContentColor.current.copy(alpha = 0.2f)
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
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
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val selectedDate = Calendar.getInstance().apply {
                                    timeInMillis = datePickerState.selectedDateMillis!!
                                }
                                if (selectedDate.get(Calendar.DATE) == selectedDate.get(Calendar.DATE) || selectedDate.after(
                                        Calendar.getInstance()
                                    )
                                ) {
                                    Toast.makeText(
                                        context,
                                        "Selected date ${dateFormatter.format(selectedDate.time)} saved",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    showDatePicker = false
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Selected date should be after today, please select again",
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
                TimePickerDialog(
                    onDismissRequest = { showTimePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val selectedTime = Calendar.getInstance().apply {
                                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                    set(Calendar.MINUTE, timePickerState.minute)
                                }
                                if (selectedTime.after(Calendar.getInstance())) {
                                    Toast.makeText(
                                        context,
                                        "Selected time ${
                                            SimpleDateFormat(
                                                "hh:mm",
                                                Locale.getDefault()
                                            ).format(selectedTime.time)
                                        } saved",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    showTimePicker = false
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Selected time should be after current time, please select again",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                val selectedDate = Calendar.getInstance().apply {
                                    timeInMillis = datePickerState.selectedDateMillis!!
                                }

                                selectedTime.set(Calendar.DATE, selectedDate.get(Calendar.DATE))
                                combinedDate = selectedTime.time
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = { }
                )
                {
                    TimePicker(state = timePickerState)
                }
            }

            if(state.isLoading) {
                Box(
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.CircularProgressIndicator()
                }
            }
        }
    }
}

