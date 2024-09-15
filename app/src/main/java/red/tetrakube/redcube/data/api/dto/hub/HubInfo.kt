package red.tetrakube.redcube.data.api.dto.hub

import kotlinx.serialization.Serializable
import red.tetrakube.redcube.data.api.dto.room.Room

@Serializable
class HubInfo(
    val slug: String,
    val name: String,
    val rooms: List<Room>
)