package com.example.todonode.presentation.completed_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todonode.TestFinishedViewModel
import com.example.todonode.presentation.Screen
import com.example.todonode.presentation.home_screen.componants.TodoItem
import java.time.LocalDateTime
import java.util.Base64

@OptIn(ExperimentalSharedTransitionApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SharedTransitionScope.FinishedScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: TestFinishedViewModel = hiltViewModel()
) {
//    val lifeCycleOwner = LocalLifecycleOwner.current
//    val backgroundColor = MaterialTheme.colorScheme.background
//    val darkTheme = isSystemInDarkTheme()

    val state by viewModel.state.collectAsState()
    val todos = state.todos?.todos ?: emptyList()

//   val view = LocalView.current
//    SideEffect {
//        val window = (view.context as Activity).window
//        window.statusBarColor = backgroundColor.toArgb()
//        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
//    }

    Box(
        modifier = modifier
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
                    isFinished = todo.finished,
                    onItemClicked = { id, title, description, deadline, isFinished ->
                        val titleString = Base64.getUrlEncoder().encodeToString(title.toByteArray())
                        val bodyString =
                            Base64.getUrlEncoder().encodeToString(description.toByteArray())
                        navController.navigate(Screen.UpdateTodoScreen.route + "?id=$id&title=$titleString&description=$bodyString&deadline=$deadline&isFinished=$isFinished")
                    },
                    onCheckBoxClicked = {

                    },
                    animatedVisibilityScope = animatedVisibilityScope
                )
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
