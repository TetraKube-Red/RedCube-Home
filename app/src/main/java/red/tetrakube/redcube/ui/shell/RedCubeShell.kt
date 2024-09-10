package red.tetrakube.redcube.ui.shell

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import red.tetrakube.redcube.navigation.Routes
import red.tetrakube.redcube.navigation.graphs.addWelcomeGraph
import red.tetrakube.redcube.ui.splash.SplashScreen
import red.tetrakube.redcube.ui.splash.SplashViewModel
import red.tetrakube.redcube.ui.theme.RedCubeHomeTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RedCubeShell() {
    SharedTransitionLayout {
        val navController = rememberNavController()

        RedCubeHomeTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {

                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Routes.Splash
                ) {
                    composable<Routes.Splash> {
                        SplashScreen(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            viewModel(factory = SplashViewModel.Factory),
                            this@SharedTransitionLayout,
                            this@composable
                        )
                    }
                    addWelcomeGraph(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        this@SharedTransitionLayout
                    )
                }
            }
        }
    }
}