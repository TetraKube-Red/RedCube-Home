package red.tetrakube.redcube.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object Splash : Routes()

    @Serializable
    data object Onboarding: Routes()

    @Serializable
    data object Welcome: Routes()

    @Serializable
    data object Login: Routes()

    @Serializable
    data object Main: Routes()

    @Serializable
    data object IoT: Routes()

}