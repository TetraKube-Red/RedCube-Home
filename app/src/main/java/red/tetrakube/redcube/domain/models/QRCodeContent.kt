package red.tetrakube.redcube.domain.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
class QRCodeContent(
    @JsonNames("api_url")
    val apiUrl: String,
    @JsonNames("websocket_url")
    val websocketUrl: String,
    @JsonNames("auth_token")
    val authToken: String,
)