package red.tetrakube.redcube.navigation.graphs

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import red.tetrakube.redcube.navigation.Routes
import red.tetrakube.redcube.ui.main.iot.IoTScreen
import red.tetrakube.redcube.ui.main.iot.IoTViewModel

fun NavGraphBuilder.addMainGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    navigation<Routes.Main>(
        startDestination = Routes.IoT,
    ) {
        composable<Routes.IoT> {
            val iotViewModel: IoTViewModel = viewModel(factory = IoTViewModel.Factory)
            IoTScreen(iotViewModel)
        }
    }
}