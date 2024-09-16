package red.tetrakube.redcube.ui.main.iot

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import red.tetrakube.redcube.RedCubeApplication
import red.tetrakube.redcube.domain.usecase.hub.StreamMinimalActiveHub
import red.tetrakube.redcube.ui.main.iot.models.IoTScreenState

class IoTViewModel(
    private val streamMinimalActiveHub: StreamMinimalActiveHub
) : ViewModel() {

    private val _screenState = mutableStateOf<IoTScreenState>(IoTScreenState.Neutral)
    val screenState: State<IoTScreenState>
        get() = _screenState

    suspend fun getHubData() {
        _screenState.value = IoTScreenState.LoadingHub
        streamMinimalActiveHub.invoke()
            .collect {
                it?.let {
                    _screenState.value = IoTScreenState.HubLoaded(it)
                }
            }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = (this[APPLICATION_KEY] as RedCubeApplication).appContainer
                val streamMinimalActiveHub = StreamMinimalActiveHub(appContainer.hubDataSource)
                IoTViewModel(
                    streamMinimalActiveHub
                )
            }
        }
    }
}