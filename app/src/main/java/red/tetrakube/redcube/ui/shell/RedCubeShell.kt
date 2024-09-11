package red.tetrakube.redcube.ui.shell

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import red.tetrakube.redcube.navigation.Routes
import red.tetrakube.redcube.navigation.graphs.addWelcomeGraph
import red.tetrakube.redcube.ui.splash.SplashScreen
import red.tetrakube.redcube.ui.splash.SplashViewModel
import red.tetrakube.redcube.ui.theme.RedCubeHomeTheme

@Composable
fun RedCubeShell() {
    val navController = rememberNavController()

    RedCubeHomeTheme {
        NavHost(
            navController = navController,
            startDestination = Routes.Splash,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            composable<Routes.Splash> {
                SplashScreen(
                    modifier = Modifier,
                    navController = navController,
                    viewModel(factory = SplashViewModel.Factory)
                )
            }
            addWelcomeGraph(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}