package red.tetrakube.redcube.data.api.mappers

import red.tetrakube.redcube.data.api.dto.hub.HubInfo
import red.tetrakube.redcube.data.db.entities.HubEntity

fun HubInfo.toEntity(
    active: Boolean,
    apiURI: String,
    websocketURI: String,
    token: String
): HubEntity =
    HubEntity(
        null,
        this.slug,
        this.name,
        active,
        token,
        apiURI,
        websocketURI
    )