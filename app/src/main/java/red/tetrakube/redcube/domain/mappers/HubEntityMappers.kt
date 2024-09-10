package red.tetrakube.redcube.domain.mappers

import red.tetrakube.redcube.data.datasource.db.entities.HubEntity
import red.tetrakube.redcube.domain.models.MinimalActiveHub

fun HubEntity.toMinimalActiveHub(): MinimalActiveHub {
    return MinimalActiveHub(
        slug = this.slug,
        name = this.name,
        token = this.connectivity.token,
        apiURI = this.connectivity.apiURI
    )
}