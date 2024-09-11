package red.tetrakube.redcube.ui.onboarding.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    LoginScreenUI(modifier)
}

@Composable
fun LoginScreenUI(modifier: Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("Hello")
        }
    }
}