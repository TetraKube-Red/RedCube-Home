package red.tetrakube.redcube.domain.mappers

import red.tetrakube.redcube.data.db.entities.HubEntity
import red.tetrakube.redcube.domain.models.MinimalActiveHub

fun HubEntity.toMinimalActiveHub(): MinimalActiveHub {
    return MinimalActiveHub(
        slug = this.slug,
        name = this.name,
        token = this.token,
        apiURI = this.apiURI
    )
}