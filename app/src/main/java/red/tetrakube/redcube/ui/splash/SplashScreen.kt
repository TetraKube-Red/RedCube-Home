package red.tetrakube.redcube.ui.splash

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import red.tetrakube.redcube.R
import red.tetrakube.redcube.navigation.Routes
import red.tetrakube.redcube.ui.splash.model.SplashScreenState

@Composable
fun SplashScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: SplashViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.loadActiveHub()
    }

    val screenState = viewModel.screenState.value
    if (screenState is SplashScreenState.Finished) {
        if (screenState.activeHub == null) {
            navController.navigate(route = Routes.Onboarding) {
                launchSingleTop = true
                popUpTo<Routes.Splash> {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(route = Routes.IoT) {
                launchSingleTop = true
                popUpTo<Routes.Splash> {
                    inclusive = true
                }
            }
        }
    }

    SplashScreenUI(modifier)
}

@Composable
fun SplashScreenUI(modifier: Modifier) {
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "RedCube Home",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(R.drawable.welcome),
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        BorderStroke(3.dp, rainbowColorsBrush),
                        RoundedCornerShape(16.dp)
                    ),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(32.dp))
            CircularProgressIndicator()
        }
    }
}