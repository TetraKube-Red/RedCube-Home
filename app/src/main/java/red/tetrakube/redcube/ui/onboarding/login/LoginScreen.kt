package red.tetrakube.redcube.ui.onboarding.login

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.common.util.concurrent.ListenableFuture
import red.tetrakube.redcube.R
import red.tetrakube.redcube.domain.usecase.barcode.BarcodeAnalyzer

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    SideEffect {
        cameraPermissionState.run { launchPermissionRequest() }
    }

    LoginScreenUI(
        modifier = modifier,
        cameraPermission = cameraPermissionState.status
    )
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenUI(modifier: Modifier, cameraPermission: PermissionStatus) {
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
            CardCameraPermission(cameraPermission)
            /*if (cameraPermission.isGranted) {
                val localContext = LocalContext.current
                val lifecycleOwner = LocalLifecycleOwner.current
                val cameraProviderFuture = remember {
                    ProcessCameraProvider.getInstance(localContext)
                }
                CameraScreen(lifecycleOwner, cameraProviderFuture) {}
            } else if (cameraPermission.shouldShowRationale) {
                Text("Camera Permission permanently denied")
            } else {
                Text("No Camera Permission")
            }*/



        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CardCameraPermission(cameraPermission: PermissionStatus) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors().copy(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .height(if (!cameraPermission.isGranted) 100.dp else 40.dp)
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
    lifecycleOwner: LifecycleOwner,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    onQRCodeDetection: (String) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
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