package red.tetrakube.redcube.data.api.mappers

import red.tetrakube.redcube.data.api.dto.room.Room
import red.tetrakube.redcube.data.db.entities.RoomEmbedded

fun Room.toEntity(): RoomEmbedded {
    val roomEmbedded = RoomEmbedded()
    roomEmbedded.name = this.name
    roomEmbedded.slug = this.slug
    return roomEmbedded
}