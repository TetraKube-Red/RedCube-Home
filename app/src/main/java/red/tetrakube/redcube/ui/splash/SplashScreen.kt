package red.tetrakube.redcube.ui.splash

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import red.tetrakube.redcube.R
import red.tetrakube.redcube.navigation.Routes
import red.tetrakube.redcube.ui.splash.model.SplashScreenState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SplashScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: SplashViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
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
        }
    }

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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        with(sharedTransitionScope) {
            Text(
                "RedCube Home",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.sharedElement(
                    sharedTransitionScope.rememberSharedContentState(key = stringResource(R.string.title_transition_id)),
                    animatedVisibilityScope = animatedContentScope
                )
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
                    )
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = stringResource(R.string.logo_transition_id)),
                        animatedVisibilityScope = animatedContentScope
                    ),
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        CircularProgressIndicator()
    }
}