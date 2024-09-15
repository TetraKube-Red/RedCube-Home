package red.tetrakube.redcube.domain.mappers

import red.tetrakube.redcube.data.db.entities.HubEntity
import red.tetrakube.redcube.domain.models.MinimalActiveHub

fun HubEntity.toMinimalActiveHub(): MinimalActiveHub? {
    return this.connectivity
        ?.let {
            MinimalActiveHub(
                slug = this.slug,
                name = this.name,
                token = it.token,
                apiURI = it.apiURI
            )
        }
}