package red.tetrakube.redcube.navigation.graphs

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import red.tetrakube.redcube.navigation.Routes
import red.tetrakube.redcube.ui.onboarding.login.LoginScreen
import red.tetrakube.redcube.ui.onboarding.welcome.WelcomeScreen

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addWelcomeGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    navigation<Routes.Onboarding>(startDestination = Routes.Welcome) {
        composable<Routes.Welcome> {
            WelcomeScreen(
                modifier,
                navController,
                sharedTransitionScope,
                this@composable
            )
        }
        composable<Routes.Login> {
            LoginScreen(
                modifier,
                navController
            )
        }
    }
}