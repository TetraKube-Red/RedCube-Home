package red.tetrakube.redcube.ui.onboarding.login.models

import red.tetrakube.redcube.domain.models.MinimalActiveHub

sealed interface LoginScreenState {

    data object Neutral : LoginScreenState

    data object Loading : LoginScreenState

    data class Finished(val activeHub: MinimalActiveHub?) : LoginScreenState

}