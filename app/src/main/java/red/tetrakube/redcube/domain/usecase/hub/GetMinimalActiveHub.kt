package red.tetrakube.redcube.domain.usecase.hub

import red.tetrakube.redcube.data.db.datasource.HubDao
import red.tetrakube.redcube.domain.mappers.toMinimalActiveHub
import red.tetrakube.redcube.domain.models.MinimalActiveHub

class GetMinimalActiveHub(
    val hubDataSource: HubDao
) {

    suspend operator fun invoke(): MinimalActiveHub? =
        hubDataSource.getActiveHub()?.toMinimalActiveHub()

}