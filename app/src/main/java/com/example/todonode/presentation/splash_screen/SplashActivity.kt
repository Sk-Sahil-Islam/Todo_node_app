package com.example.todonode.presentation.splash_screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todonode.MainActivity
import com.example.todonode.R
import com.example.todonode.TestViewModel2
import com.example.todonode.ui.theme.PrimaryDark
import com.example.todonode.ui.theme.TodoNodeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SplashScreen()

        }
    }

    @Composable
    private fun SplashScreen(
        authViewModel: TestViewModel2 = hiltViewModel()
    ) {
        val view = LocalView.current
        val darkTheme = isSystemInDarkTheme()
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = PrimaryDark.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !darkTheme
        }
        val user = authViewModel.getUser.collectAsState()
        val alpha = remember {
            Animatable(0f)
        }

        LaunchedEffect(key1 = true) {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(500)
            )
            delay(500)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.putExtra("user", user.value)
            startActivity(intent)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryDark)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.alpha(alpha.value),
                painter = painterResource(id = R.drawable.logo_png),
                contentDescription = null
            )
            LinearProgressIndicator(
                modifier = Modifier.align(Alignment.BottomCenter),
                progress = alpha.value,
                color = PrimaryDark,
                strokeCap = StrokeCap.Round
            )
        }
    }
}
