package red.tetrakube.redcube.data.api.mappers

import red.tetrakube.redcube.data.api.dto.room.Room
import red.tetrakube.redcube.data.db.entities.RoomEntity

fun Room.toEntity(hubId: Long) =
    RoomEntity(
        null,
        this.slug,
        this.name,
        hubId
    )
