package red.tetrakube.redcube.ui.main.iot.models

import red.tetrakube.redcube.domain.models.MinimalActiveHub

sealed class IoTScreenState {

    data object Neutral : IoTScreenState()
    data object LoadingHub : IoTScreenState()
    data class HubLoaded(val minimalActiveHub: MinimalActiveHub) : IoTScreenState()

}