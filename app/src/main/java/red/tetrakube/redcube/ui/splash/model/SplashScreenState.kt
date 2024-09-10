package red.tetrakube.redcube.ui.splash.model

import red.tetrakube.redcube.domain.models.MinimalActiveHub

sealed interface SplashScreenState {

    data object Neutral : SplashScreenState

    data object Loading : SplashScreenState

    data class Finished(val activeHub: MinimalActiveHub?) : SplashScreenState

}