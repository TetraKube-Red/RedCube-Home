package red.tetrakube.redcube.data.api.mappers

import io.realm.kotlin.ext.toRealmList
import red.tetrakube.redcube.data.api.dto.hub.HubInfo
import red.tetrakube.redcube.data.db.entities.HubConnectivityEmbedded
import red.tetrakube.redcube.data.db.entities.HubEntity

fun HubInfo.toEntity(
    active: Boolean,
    apiURI: String,
    websocketURI: String,
    token: String
): HubEntity {
    val entity = HubEntity()
    entity.slug = this.slug
    entity.active = active
    entity.name = this.name
    entity.rooms = this.rooms.map { r -> r.toEntity() }.toRealmList()
    entity.connectivity = HubConnectivityEmbedded()
    entity.connectivity!!.apiURI = apiURI
    entity.connectivity!!.token = token
    entity.connectivity!!.websocketURI = websocketURI
    return entity
}