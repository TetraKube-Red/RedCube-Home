package red.tetrakube.redcube.domain.usecase.hub

import red.tetrakube.redcube.data.db.datasource.HubDataSource

class IsThereActiveHub(
    private val hubDataSource: HubDataSource
) {

    operator fun invoke(): Boolean =
        hubDataSource.getActiveHub() != null

}