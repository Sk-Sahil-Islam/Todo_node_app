package com.example.todonode.presentation.signup_screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.todonode.TestViewModel
import com.example.todonode.TestViewModel2
import com.example.todonode.data.remote.dto.RegisterRequest
import com.example.todonode.presentation.Screen
import com.example.todonode.presentation.login_screen.componants.ConfirmPasswordTextField
import com.example.todonode.presentation.login_screen.componants.CustomButton
import com.example.todonode.presentation.login_screen.componants.EmailTextField
import com.example.todonode.presentation.login_screen.componants.PasswordTextField
import com.example.todonode.ui.theme.LinkDark
import com.example.todonode.ui.theme.LinkLight
import com.example.todonode.ui.theme.SignUpBackgroundColorDark
import com.example.todonode.ui.theme.SignUpBackgroundColorLight
import com.example.todonode.ui.theme.SignUpOnBackgroundDark
import com.example.todonode.ui.theme.SignUpOnBackgroundLight
import com.example.todonode.ui.theme.SignUpPrimaryDark
import com.example.todonode.ui.theme.SignUpPrimaryLight
import com.example.todonode.utils.isValidEmail
import com.example.todonode.utils.isValidPassword

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TestViewModel = hiltViewModel()
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val state by viewModel.registerState.collectAsState()
    val darkTheme = isSystemInDarkTheme()
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val primaryColor = if (darkTheme) SignUpPrimaryDark else SignUpPrimaryLight
    val backgroundColor =
        if (darkTheme) SignUpBackgroundColorDark else SignUpBackgroundColorLight
    val onBackGroundColor =
        if (darkTheme) SignUpOnBackgroundDark else SignUpOnBackgroundLight

    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = backgroundColor.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }

    LaunchedEffect(state.error) {
        if (state.error.isNotBlank())
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }
    LaunchedEffect(state.response?.success) {
        if (state.response?.success == true) {
            val currentState = lifeCycleOwner.lifecycle.currentState
            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                navController.navigate(Screen.HomeScreen.route){
                    popUpTo(0)
                }
            }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier.fillMaxWidth()
            ) {

                Text(
                    modifier = Modifier.offset(y = (10).dp),
                    text = "Let's",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = onBackGroundColor.copy(0.5f)
                )
                Text(
                    text = "Start!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 60.sp,
                    color = primaryColor.copy(0.95f)
                )
            }


            Spacer(modifier = Modifier.size(16.dp))

            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EmailTextField(
                    email = userName,
                    onValueChange = {
                        userName = it
                    },
                    label = {
                        Text(
                            text = "Username"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp),
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null
                        )
                    },
                    isError = isError,
                    primaryColor = primaryColor,
                    onBackgroundColor = onBackGroundColor
                )

                EmailTextField(
                    email = email,
                    onValueChange = {
                        email = it
                    },
                    label = {
                        Text(
                            text = "Email"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp),
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null
                        )
                    },
                    isError = isError,
                    primaryColor = primaryColor,
                    onBackgroundColor = onBackGroundColor
                )
                PasswordTextField(
                    password = password,
                    onValueChange = {
                        password = it
                    },
                    passwordVisible = passwordVisible,
                    onVisibilityClick = {
                        passwordVisible = !passwordVisible
                    },
                    primaryColor = primaryColor,
                    onBackgroundColor = onBackGroundColor
                )
                ConfirmPasswordTextField(
                    password = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                    },
                    passwordVisible = passwordVisible,
                    primaryColor = primaryColor,
                    onBackgroundColor = onBackGroundColor
                )
            }
            Spacer(modifier = Modifier.size(30.dp))
            CustomButton(
                modifier = Modifier,
                onClick = {
                    if (isValidEmail(email) && isValidPassword(password)) {
                        isError = false
                        viewModel.registerUser(
                            RegisterRequest(
                                userName = userName,
                                email = email,
                                password = password
                            )
                        )
                    } else {
                        isError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                if(!state.isLoading)
                    Text(modifier = Modifier.padding(vertical = 3.dp), text = "Sign up", fontSize = 20.sp, color = backgroundColor)
                else
                    CircularProgressIndicator(color = backgroundColor)

            }

            Spacer(modifier = Modifier.size(6.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val interactionSource = remember { MutableInteractionSource() }
                Text(text = "Already have an account?", fontSize = 14.sp)
                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = interactionSource,
                        onClick = {
                            navController.navigate(Screen.LoginScreen.route){
                                popUpTo(0)
                            }
                        }
                    ),
                    text = "Login",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSystemInDarkTheme()) LinkDark else LinkLight
                )
            }
        }
    }
}