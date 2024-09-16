package red.tetrakube.redcube.domain.usecase.hub

import kotlinx.coroutines.flow.map
import red.tetrakube.redcube.data.db.datasource.HubDao
import red.tetrakube.redcube.domain.mappers.toMinimalActiveHub

class StreamMinimalActiveHub(
    private val hubDataSource: HubDao
) {

    operator fun invoke() =
        hubDataSource.streamActiveHub()
            .map { it.toMinimalActiveHub() }
}