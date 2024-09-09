package red.tetrakube.redcube.ui.onboarding.welcome

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import red.tetrakube.redcube.R
import red.tetrakube.redcube.navigation.Routes

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
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

    with(sharedTransitionScope) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                "RedCube Home",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.sharedElement(
                    sharedTransitionScope.rememberSharedContentState(key = stringResource(R.string.title_transition_id)),
                    animatedVisibilityScope = animatedContentScope
                )
            )
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

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = "🚀 Welcome to RedCube Home!"
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = MaterialTheme.typography.bodyLarge.toSpanStyle()) {
                            append("Hi there! It looks like this is your first time using our app, and we’re excited to have you on board!\n")
                            append("To get started, you'll need to log in. But don't worry, it's easy!\n")
                        }
                    }
                )

                Row() {
                    Image(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        painter = painterResource(R.drawable.computer_24px),
                        contentDescription = null
                    )
                    Text("First, generate a QR code using the TetraKube Platform CLI - see the TetraKube CLI component on GitHub")
                }

                Row() {
                    Image(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        painter = painterResource(R.drawable.qr_code_scanner_24px),
                        contentDescription = null
                    )
                    Text("Once you have your QR code, just scan it with your phone’s camera")
                }

                Row() {
                    Image(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        painter = painterResource(R.drawable.play_circle_24px),
                        contentDescription = null
                    )
                    Text("Let’s get your home management journey started!")
                }
            }

            ElevatedButton(onClick = {
                navController.navigate(Routes.Login)
            }) {
                Text("I'm ready, scan QR Code")
            }
        }
    }
}