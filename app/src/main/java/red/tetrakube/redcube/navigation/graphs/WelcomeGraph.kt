package red.tetrakube.redcube.navigation.graphs

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import red.tetrakube.redcube.navigation.Routes
import red.tetrakube.redcube.ui.onboarding.login.LoginScreen
import red.tetrakube.redcube.ui.onboarding.login.LoginViewModel
import red.tetrakube.redcube.ui.onboarding.welcome.WelcomeScreen

fun NavGraphBuilder.addWelcomeGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    navigation<Routes.Onboarding>(
        startDestination = Routes.Welcome,
    ) {
        composable<Routes.Welcome> {
            WelcomeScreen(
                modifier,
                navController
            )
        }
        composable<Routes.Login> {
            LoginScreen(
                modifier,
                navController,
                viewModel(factory = LoginViewModel.Factory)
            )
        }
    }
}