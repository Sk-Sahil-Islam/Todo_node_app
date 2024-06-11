package com.example.todonode

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.todonode.data.remote.dto.UserAuth
import com.example.todonode.presentation.Screen
import com.example.todonode.presentation.add_todo_screen.AddTodoScreen
import com.example.todonode.presentation.completed_screen.FinishedScreen
import com.example.todonode.presentation.home_screen.HomeScreen
import com.example.todonode.presentation.login_screen.LoginScreen
import com.example.todonode.presentation.signup_screen.SignUpScreen
import com.example.todonode.presentation.update_todo_screen.UpdateTodoScreen
import com.example.todonode.ui.theme.BackgroundDark
import com.example.todonode.ui.theme.TodoNodeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Base64

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoNodeTheme {
                val viewModel2: TestViewModel2 by viewModels()
                val state by viewModel2.loginState.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val user = intent.getStringExtra("user") ?: ""

                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    LaunchedEffect(selectedItemIndex) {
                        selectedItemIndex = when (currentRoute) {
                            Screen.HomeScreen.route -> 0
                            Screen.SignUpScreen.route -> 1
                            Screen.LoginScreen.route -> 2
                            else -> 0
                        }
                    }

                    val items = listOf(
                        NavigationItems(
                            Screen.HomeScreen.route,
                            "Home",
                            Icons.Outlined.Home,
                            Icons.Default.Home,
                            onClick = {
                                navController.navigate(Screen.HomeScreen.route)
                            }
                        ),
                        NavigationItems(
                            Screen.SignUpScreen.route,
                            "Completed",
                            Icons.Outlined.CheckCircle,
                            Icons.Default.CheckCircle,
                            onClick = {
                                navController.navigate(Screen.FinishedScreen.route)
                            }
                        )
                    )

                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet(
                                modifier = Modifier.fillMaxWidth(0.75f),
                                drawerContainerColor = MaterialTheme.colorScheme.background
                            ) {
                                SideBarUser(
                                    modifier = Modifier.fillMaxWidth(),
                                    user = UserAuth(
                                        avatar = state.user?.avatar ?: "https://avatars.githubusercontent.com/u/77445921?v=4",
                                        email = state.user?.email ?: "user@gmail.com",
                                        userName = state.user?.userName ?: "User name",
                                        _id = state.user?._id ?: "1",
                                        __v = state.user?.__v ?: 0
                                    )
                                )
                                items.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        label = {
                                            Text(text = item.title)
                                        },
                                        selected = index == selectedItemIndex,
                                        onClick = {
                                            selectedItemIndex = index
                                            scope.launch {
                                                drawerState.close()
                                                item.onClick.invoke()
                                            }
                                        },
                                        icon = {
                                            if (item.selectedIcon1 is ImageVector) {
                                                Icon(
                                                    imageVector = if (index == selectedItemIndex) {
                                                        item.selectedIcon1
                                                    } else item.unselectedIcon1 as ImageVector,
                                                    contentDescription = item.title
                                                )
                                            } else {
                                                Icon(
                                                    painter = if (index == selectedItemIndex) {
                                                        item.selectedIcon1 as Painter
                                                    } else item.unselectedIcon1 as Painter,
                                                    contentDescription = item.title
                                                )
                                            }
                                        },
                                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                                        colors = NavigationDrawerItemDefaults.colors(
                                            selectedContainerColor = Color.Transparent,
                                            unselectedContainerColor = Color.Transparent,
                                            selectedIconColor = MaterialTheme.colorScheme.primary,
                                            selectedTextColor = MaterialTheme.colorScheme.primary,
                                            unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                                            unselectedIconColor = MaterialTheme.colorScheme.onBackground
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                NavigationDrawerItem(
                                    label = {
                                        Text(text = "Logout")
                                    },
                                    selected = items.size + 1 == selectedItemIndex,
                                    onClick = {
                                        selectedItemIndex = items.size + 1
                                        scope.launch {
                                            drawerState.close()
                                            viewModel2.logoutUser()
                                            navController.navigate(Screen.LoginScreen.route) {
                                                popUpTo(0)
                                            }
                                        }
                                    },
                                    icon = {
                                        if (selectedItemIndex == items.size + 1) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_logout),
                                                tint = MaterialTheme.colorScheme.primary,
                                                contentDescription = "Logout"
                                            )
                                        } else {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_logout),
                                                contentDescription = "Logout"
                                            )
                                        }
                                    },
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                                    colors = NavigationDrawerItemDefaults.colors(
                                        selectedContainerColor = Color.Transparent,
                                        unselectedContainerColor = Color.Transparent,
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                                        unselectedIconColor = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                            }
                        }
                    ) {

                        Scaffold(
                            topBar = {
                                if (currentRoute == Screen.HomeScreen.route) {
                                    TopAppBar(
                                        title = {
                                            Text(text = "Todo Node", fontSize = 24.sp)
                                        },
                                        navigationIcon = {
                                            IconButton(onClick = {
                                                scope.launch {
                                                    drawerState.open()
                                                }
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Outlined.Menu,
                                                    contentDescription = "Drawer"
                                                )
                                            }
                                        },
                                        actions = {
                                            IconButton(onClick = {

                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.Person,
                                                    contentDescription = "Profile"
                                                )
                                            }
                                        },
                                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                                    )
                                }
                            }
                        ) { paddingValues ->
                            SharedTransitionLayout {

                                NavHost(
                                    navController = navController,
                                    startDestination = if (user.isBlank()) Screen.LoginScreen.route else Screen.HomeScreen.route
                                ) {
                                    composable(Screen.LoginScreen.route) {
                                        LoginScreen(
                                            navController = navController,
                                            modifier = Modifier.padding(paddingValues)
                                        )
                                    }
                                    composable(Screen.SignUpScreen.route) {
                                        SignUpScreen(
                                            navController = navController,
                                            modifier = Modifier.padding(paddingValues)
                                        )
                                    }
                                    composable(Screen.HomeScreen.route) {
                                        HomeScreen(
                                            navController = navController,
                                            modifier = Modifier.padding(paddingValues),
                                            animatedVisibilityScope = this
                                        )
                                    }
                                    composable(Screen.AddTodoScreen.route) {
                                        AddTodoScreen(
                                            navController = navController,
                                            modifier = Modifier.padding(paddingValues)
                                        )
                                    }
                                    composable(Screen.FinishedScreen.route) {
                                        FinishedScreen(
                                            navController = navController,
                                            modifier = Modifier.padding(paddingValues),
                                            animatedVisibilityScope = this
                                        )
                                    }
                                    composable(
                                        Screen.UpdateTodoScreen.route + "?id={id}&title={title}&description={description}&deadline={deadline}&isFinished={isFinished}",
                                        arguments = listOf(
                                            navArgument(name = "id") {
                                                type = NavType.StringType
                                                nullable = true
                                            },
                                            navArgument(name = "title") {
                                                type = NavType.StringType
                                                nullable = true
                                            },
                                            navArgument(name = "description") {
                                                type = NavType.StringType
                                                nullable = true
                                            },
                                            navArgument(name = "deadline") {
                                                type = NavType.StringType
                                                nullable = true
                                            },
                                            navArgument(name = "isFinished") {
                                                type = NavType.BoolType
                                                defaultValue = false
                                            }
                                        ),
                                    ) {
                                        val id = it.arguments?.getString("id")
                                        val title = it.arguments?.getString("title")
                                        val description = it.arguments?.getString("description")
                                        val deadline = it.arguments?.getString("deadline")
                                        val isFinished = it.arguments?.getBoolean("isFinished")
                                        UpdateTodoScreen(
                                            navController = navController,
                                            todoId = id.orEmpty(),
                                            todoTitle = String(
                                                Base64.getUrlDecoder().decode(title)
                                            ),
                                            todoDescription = String(
                                                Base64.getUrlDecoder().decode(description)
                                            ),
                                            todoDeadline = deadline.orEmpty(),
                                            isFinished = isFinished ?: false,
                                            animatedVisibilityScope = this
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

data class NavigationItems(
    val route: String,
    val title: String,
    val unselectedIcon1: Any,
    val selectedIcon1: Any,
    val onClick: () -> Unit
) {
    constructor(
        route: String,
        title: String,
        unselectedIcon: ImageVector,
        selectedIcon: ImageVector,
        onClick: () -> Unit
    ) :
            this(
                route = route,
                title = title,
                unselectedIcon1 = unselectedIcon,
                selectedIcon1 = selectedIcon,
                onClick = onClick
            )

    constructor(
        route: String,
        title: String,
        unselectedIcon: Painter,
        selectedIcon: Painter,
        onClick: () -> Unit
    ) :
            this(
                route = route,
                title = title,
                unselectedIcon1 = unselectedIcon,
                selectedIcon1 = selectedIcon,
                onClick = onClick
            )
}

@Composable
fun SideBarUser(
    modifier: Modifier = Modifier,
    user: UserAuth
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary,
                        Color.White
                    )
                )
            )
            .padding(16.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    model = user.avatar,
                    placeholder = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "profile",
                    error = painterResource(id = R.drawable.ic_profile),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = user.userName,
                    fontSize = 18.sp,
                    color = BackgroundDark,
                    fontWeight = FontWeight.Bold
                )
                Text(text = user.email, fontSize = 14.sp, color = BackgroundDark)
            }
        }
    }
}