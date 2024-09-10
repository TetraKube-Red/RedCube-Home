package red.tetrakube.redcube.domain.usecase.hub

import red.tetrakube.redcube.data.datasource.db.HubDataSource
import red.tetrakube.redcube.domain.mappers.toMinimalActiveHub
import red.tetrakube.redcube.domain.models.MinimalActiveHub

class GetMinimalActiveHub(
    val hubDataSource: HubDataSource
) {

    operator fun invoke(): MinimalActiveHub? =
        hubDataSource.getActiveHub()?.toMinimalActiveHub()

}