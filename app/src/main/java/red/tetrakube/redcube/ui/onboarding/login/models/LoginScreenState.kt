package red.tetrakube.redcube.ui.onboarding.login.models

import red.tetrakube.redcube.domain.models.MinimalActiveHub
import red.tetrakube.redcube.domain.models.UseCasesErrors

sealed interface LoginScreenState {

    data object Neutral : LoginScreenState

    data object Loading : LoginScreenState

    data class Finished(val activeHub: MinimalActiveHub?) : LoginScreenState

    data class FinishedWithError(val useCasesErrors: UseCasesErrors): LoginScreenState

}