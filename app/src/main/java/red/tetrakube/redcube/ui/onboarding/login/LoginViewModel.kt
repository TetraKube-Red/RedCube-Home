package red.tetrakube.redcube.ui.onboarding.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import red.tetrakube.redcube.RedCubeApplication

class LoginViewModel : ViewModel() {

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