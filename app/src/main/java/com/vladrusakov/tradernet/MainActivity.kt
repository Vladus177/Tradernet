package com.vladrusakov.tradernet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Surface
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.vladrusakov.tradernet.presentation.navigation.NavigationComponent
import com.vladrusakov.tradernet.presentation.navigation.Navigator
import com.vladrusakov.tradernet.ui.theme.TradernetTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TradernetTheme {
                TraderNetApp(navigator = navigator)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TraderNetApp(navigator: Navigator) {
    val navController = rememberAnimatedNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold { innerPadding ->
            NavigationComponent(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                navigator = navigator,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TradernetTheme {

    }
}