package red.tetrakube.redcube.ui.onboarding.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import red.tetrakube.redcube.RedCubeApplication
import red.tetrakube.redcube.domain.models.QRCodeContent
import red.tetrakube.redcube.domain.models.UseCasesErrors
import red.tetrakube.redcube.domain.usecase.hub.RetrieveHubInfoFromAPI
import red.tetrakube.redcube.ui.onboarding.login.models.LoginScreenState

class LoginViewModel(
    private val retrieveHubInfoFromAPI: RetrieveHubInfoFromAPI
) : ViewModel() {

    private val _loginScreenState = mutableStateOf<LoginScreenState>(LoginScreenState.Neutral)
    val loginScreenState: State<LoginScreenState>
        get() = _loginScreenState

    fun handleHubEnrollment(qrContent: String) {
        viewModelScope.launch {
            _loginScreenState.value = LoginScreenState.Loading
            val qrCodeContent = Json.decodeFromString<QRCodeContent>(qrContent)
            Log.i("LoginViewModel", "QRCode decoded $qrCodeContent")

            val hubDataResult = retrieveHubInfoFromAPI.invoke(
                qrCodeContent.apiUrl,
                qrCodeContent.websocketUrl,
                qrCodeContent.authToken
            )
            if (hubDataResult.isFailure) {
                val useCasesErrors = hubDataResult.exceptionOrNull()!! as UseCasesErrors
                _loginScreenState.value = LoginScreenState.FinishedWithError(useCasesErrors)
            } else {
                _loginScreenState.value = LoginScreenState.Finished(hubDataResult.getOrNull())
            }
        }
    }

    fun resetLoginStatus() {
        _loginScreenState.value = LoginScreenState.Neutral
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = (this[APPLICATION_KEY] as RedCubeApplication).appContainer
                val retrieveHubInfoFromAPI = RetrieveHubInfoFromAPI(
                    appContainer.redCubeDatabase.hubRepository(),
                    appContainer.hubAPI
                )
                LoginViewModel(
                    retrieveHubInfoFromAPI
                )
            }
        }
    }

}