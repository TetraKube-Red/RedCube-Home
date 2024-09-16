package red.tetrakube.redcube.ui.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import red.tetrakube.redcube.RedCubeApplication
import red.tetrakube.redcube.domain.usecase.hub.GetMinimalActiveHub
import red.tetrakube.redcube.ui.splash.model.SplashScreenState

class SplashViewModel(private val getMinimalActiveHub: GetMinimalActiveHub) : ViewModel() {

    private val _screenState = mutableStateOf<SplashScreenState>(SplashScreenState.Neutral)
    val screenState: State<SplashScreenState>
        get() = _screenState

    suspend fun loadActiveHub() {
        _screenState.value = SplashScreenState.Loading
        val activeHub = getMinimalActiveHub()
        delay(500)
        _screenState.value = SplashScreenState.Finished(activeHub)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = (this[APPLICATION_KEY] as RedCubeApplication).appContainer
                val getMinimalActiveHub = GetMinimalActiveHub(
                    hubDataSource = appContainer.redCubeDatabase.hubRepository()
                )

                SplashViewModel(
                    getMinimalActiveHub
                )
            }
        }
    }

}