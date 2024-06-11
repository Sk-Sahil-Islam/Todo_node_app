package com.example.todonode.presentation.home_screen

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.todonode.TestHomeViewModel
import com.example.todonode.presentation.Screen
import com.example.todonode.presentation.home_screen.componants.TodoItem
import java.time.LocalDateTime
import java.util.Base64

@OptIn(ExperimentalSharedTransitionApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SharedTransitionScope.HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: TestHomeViewModel = hiltViewModel()
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val backgroundColor = MaterialTheme.colorScheme.background
    val darkTheme = isSystemInDarkTheme()

    val state by viewModel.homeState.collectAsState()
    val todos = state.todos?.todos ?: emptyList()

    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = backgroundColor.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val currentState = lifeCycleOwner.lifecycle.currentState
                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        navController.navigate(Screen.AddTodoScreen.route)
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Todo"
                )
            }
        }
    ) { paddingValues ->

        Box(
            modifier = modifier
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.padding(start = 11.dp, end = 11.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {

                items(todos) { todo ->
                    TodoItem(
                        id = todo._id,
                        title = todo.title,
                        description = todo.description,
                        deadline = LocalDateTime.parse(todo.deadline.dropLast(1)),
                        createdAt = LocalDateTime.parse(todo.createdAt.dropLast(1)),
                        isCompleted = false,
                        onItemClicked = { id, title, description, deadline ->
                            val titleString = Base64.getUrlEncoder().encodeToString(title.toByteArray())
                            val bodyString = Base64.getUrlEncoder().encodeToString(description.toByteArray())
                            navController.navigate(Screen.UpdateTodoScreen.route + "?id=$id&title=$titleString&description=$bodyString&deadline=$deadline")
                        },
                        onCheckBoxClicked = {

                        },
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                }

//                items(10) {
//                    TodoItem(
//                        title = "Title $it",
//                        description = "This is a description of the task $it lorem ipsum dolor sit amet, consectetur adipiscing elit.",
//                        dueTime = LocalDateTime.parse("2024-05-11T00:03:00.000"),
//                        createdAt = LocalDateTime.now(),
//                        isCompleted = false,
//                        onItemClicked = {
//
//                        },
//                        onCheckBoxClicked = {
//
//                        })
//                }
            }
        }
    }
}