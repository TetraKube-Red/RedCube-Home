package red.tetrakube.redcube.ui.onboarding.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.serialization.json.Json
import red.tetrakube.redcube.RedCubeApplication
import red.tetrakube.redcube.domain.models.QRCodeContent
import red.tetrakube.redcube.ui.onboarding.login.models.LoginScreenState

class LoginViewModel : ViewModel() {

    private val _loginScreenState = mutableStateOf<LoginScreenState>(LoginScreenState.Neutral)
    val loginScreenState: State<LoginScreenState>
        get() = _loginScreenState

    fun handleHubEnrollment(qrContent: String) {
        _loginScreenState.value = LoginScreenState.Loading
        val qrCodeContent = Json.decodeFromString<QRCodeContent>(qrContent)
        Log.i("LoginViewModel", "QRCode decoded $qrCodeContent")
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = (this[APPLICATION_KEY] as RedCubeApplication).appContainer
                LoginViewModel(
                )
            }
        }
    }

}