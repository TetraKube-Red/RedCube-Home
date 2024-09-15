package red.tetrakube.redcube.ui.onboarding.login

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import red.tetrakube.redcube.R
import red.tetrakube.redcube.domain.models.MinimalActiveHub
import red.tetrakube.redcube.domain.models.UseCasesErrors
import red.tetrakube.redcube.domain.usecase.barcode.BarcodeAnalyzer
import red.tetrakube.redcube.navigation.Routes
import red.tetrakube.redcube.ui.onboarding.login.models.LoginScreenState
import red.tetrakube.redcube.ui.onboarding.login.models.LoginTargetContent

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val loginScreenState = loginViewModel.loginScreenState
    val targetContent = remember(cameraPermissionState.status, loginScreenState.value) {
        derivedStateOf {
            if (!cameraPermissionState.status.isGranted) {
                LoginTargetContent.PERMISSION_CARD
            } else if (cameraPermissionState.status.isGranted && loginScreenState.value is LoginScreenState.Neutral) {
                LoginTargetContent.CAMERA_SCANNER
            } else if (loginScreenState.value is LoginScreenState.Loading) {
                LoginTargetContent.LOADER
            } else if (loginScreenState.value is LoginScreenState.Finished) {
                LoginTargetContent.SUCCESS
            } else if (loginScreenState.value is LoginScreenState.FinishedWithError) {
                LoginTargetContent.ERROR
            } else {
                LoginTargetContent.ERROR
            }
        }
    }

    SideEffect {
        cameraPermissionState.run { launchPermissionRequest() }
    }

    LoginScreenUI(
        modifier = modifier,
        targetContent = targetContent,
        cameraPermission = cameraPermissionState.status,
        loginScreenState = loginViewModel.loginScreenState.value,
        resetLoginStatus = loginViewModel::resetLoginStatus,
        onQRCodeDetection = loginViewModel::handleHubEnrollment,
        onFlowFinished = {
            navController.navigate(route = Routes.IoT) {
                launchSingleTop = true
                popUpTo<Routes.Splash> {
                    inclusive = true
                }
            }
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenUI(
    modifier: Modifier,
    targetContent: State<LoginTargetContent>,
    cameraPermission: PermissionStatus,
    loginScreenState: LoginScreenState,
    resetLoginStatus: () -> Unit,
    onQRCodeDetection: (String) -> Unit,
    onFlowFinished: () -> Unit
) {
    val density = LocalDensity.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Hub onboarding")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (targetContent.value == LoginTargetContent.PERMISSION_CARD) {
                AnimatedVisibility(
                    visible = targetContent.value == LoginTargetContent.PERMISSION_CARD,
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    CardCameraPermission(cameraPermission)
                }
            }

            if (targetContent.value == LoginTargetContent.CAMERA_SCANNER) {
                AnimatedVisibility(
                    visible = targetContent.value == LoginTargetContent.CAMERA_SCANNER,
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    CameraScreen(onQRCodeDetection)
                }
            }
            if (targetContent.value == LoginTargetContent.LOADER) {
                AnimatedVisibility(
                    visible = targetContent.value == LoginTargetContent.LOADER,
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    LoaderCard()
                }
            }
            if (targetContent.value == LoginTargetContent.SUCCESS) {
                AnimatedVisibility(
                    visible = targetContent.value == LoginTargetContent.SUCCESS,
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    val minimalHubIfo = (loginScreenState as LoginScreenState.Finished).activeHub
                    HubInfoViewerCard(minimalHubIfo, onFlowFinished)
                }
            }
            if (targetContent.value == LoginTargetContent.ERROR) {
                AnimatedVisibility(
                    visible = targetContent.value == LoginTargetContent.ERROR,
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    val useCasesErrors =
                        (loginScreenState as LoginScreenState.FinishedWithError).useCasesErrors
                    HubEnrollmentError(useCasesErrors, resetLoginStatus)
                }
            }
        }
    }
}

@Composable
fun HubEnrollmentError(useCasesErrors: UseCasesErrors, resetLoginStatus: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier) {
                    Icon(
                        painter = painterResource(R.drawable.device_hub_24px),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Hub login error",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.close_24px),
                    contentDescription = null,
                    tint = Color.Magenta
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "There was an error of type $useCasesErrors during login process"
            )
            Spacer(Modifier.height(16.dp))
            ElevatedButton(
                onClick = resetLoginStatus
            ) {
                Text("Try again")
            }
        }
    }
}

@Composable
fun HubInfoViewerCard(minimalHubIfo: MinimalActiveHub?, onFlowFinished: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier) {
                    Icon(
                        painter = painterResource(R.drawable.device_hub_24px),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Hub login success",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.check_24px),
                    contentDescription = null,
                    tint = Color.Magenta
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "The hub ${minimalHubIfo?.name ?: "null"} is enrolled successfully"
            )
            Spacer(Modifier.height(16.dp))
            ElevatedButton(
                onClick = onFlowFinished
            ) {
                Text("Use your hub")
            }
        }
    }
}

@Composable
fun LoaderCard() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CardCameraPermission(cameraPermission: PermissionStatus) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors()
            .copy(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier) {
                    Icon(
                        painter = painterResource(R.drawable.camera_24px),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Setup camera permissions",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                if (cameraPermission.isGranted) {
                    Icon(
                        painter = painterResource(R.drawable.check_24px),
                        contentDescription = null,
                        tint = Color.Blue
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "Grant camera permissions to allow the application to scan the QR code containing the hub information",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun CameraScreen(
    onQRCodeDetection: (String) -> Unit
) {
    val localContext = LocalContext.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(localContext)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    ElevatedCard(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .height(400.dp)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                val previewView = PreviewView(context)
                val preview = Preview.Builder().build()
                val selector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()

                preview.setSurfaceProvider(previewView.surfaceProvider)

                val imageAnalysis = ImageAnalysis.Builder().build()
                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    BarcodeAnalyzer(onQRCodeDetection)
                )

                runCatching {
                    val cameraProvider = cameraProviderFuture.get()
                    cameraProvider.unbindAll()

                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        preview,
                        imageAnalysis
                    )
                }
                    .onFailure {
                        Log.e("CAMERA", "Camera bind error ${it.localizedMessage}", it)
                    }
                previewView
            }
        )
    }
}