package com.example.todonode.presentation.login_screen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.todonode.R
import com.example.todonode.TestViewModel2
import com.example.todonode.data.remote.dto.LoginRequest
import com.example.todonode.presentation.Screen
import com.example.todonode.presentation.login_screen.componants.CustomButton
import com.example.todonode.presentation.login_screen.componants.EmailTextField
import com.example.todonode.presentation.login_screen.componants.PasswordTextField
import com.example.todonode.ui.theme.LinkDark
import com.example.todonode.ui.theme.LinkLight
import com.example.todonode.ui.theme.LoginBackgroundColorDark
import com.example.todonode.ui.theme.LoginBackgroundColorLight
import com.example.todonode.ui.theme.LoginPrimaryDark
import com.example.todonode.ui.theme.LoginPrimaryLight
import com.example.todonode.ui.theme.OnBackgroundDark
import com.example.todonode.ui.theme.OnBackgroundLight

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TestViewModel2 = hiltViewModel()
) {
    val darkTheme = isSystemInDarkTheme()
    val state by viewModel.loginState.collectAsState()
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val primaryColor = if (isSystemInDarkTheme()) LoginPrimaryDark else LoginPrimaryLight
    val backgroundColor = if (isSystemInDarkTheme()) LoginBackgroundColorDark else LoginBackgroundColorLight
    val onBackgroundColor = if (isSystemInDarkTheme()) OnBackgroundDark else OnBackgroundLight

    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = backgroundColor.toArgb() // Change this to your desired color
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }

    LaunchedEffect(state.error) {
        if(state.error.isNotBlank())
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }
    LaunchedEffect(state.response?.success) {
        if (state.response?.success == true) {
            Log.e("Successfully Login", state.response!!.token)
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

            Image(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                painter = painterResource(
                    id =
                    if (isSystemInDarkTheme())
                        R.drawable.login_bro_dark
                    else
                        R.drawable.login_bro_light
                ),
                contentDescription = "Login Bro Image",
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column(Modifier.fillMaxWidth()) {

                Text(
                    text = "Welcome back!",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
                Text(
                    text = "Please login to your account.",
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

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
                    onBackgroundColor = onBackgroundColor
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
                    onBackgroundColor = onBackgroundColor
                )
            }
            Spacer(modifier = Modifier.size(30.dp))
            CustomButton(
                modifier = Modifier,
                onClick = {
                    viewModel.loginUser(
                        LoginRequest(
                            email = email,
                            password = password
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
            ) {
                if(!state.isLoading)
                    Text(modifier = Modifier.padding(vertical = 3.dp), text = "Login", fontSize = 20.sp, color = backgroundColor)
                else
                    CircularProgressIndicator(color = backgroundColor)
            }

            Spacer(modifier = Modifier.size(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don't have an account?", fontSize = 14.sp)
                Spacer(modifier = Modifier.size(4.dp))
                val interactionSource = remember { MutableInteractionSource() }
                Text(
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = interactionSource,
                        onClick = {
                            navController.navigate(Screen.SignUpScreen.route){
                                popUpTo(Screen.LoginScreen.route)
                            }
                        }
                    ),
                    text = "Sign up",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSystemInDarkTheme()) LinkDark else LinkLight
                )
            }
        }
    }
}