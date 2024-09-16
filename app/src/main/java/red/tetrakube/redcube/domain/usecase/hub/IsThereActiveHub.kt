package red.tetrakube.redcube.domain.usecase.hub

import red.tetrakube.redcube.data.db.datasource.HubDao

class IsThereActiveHub(
    private val hubDataSource: HubDao
) {

    suspend operator fun invoke(): Boolean =
        hubDataSource.getActiveHub() != null

}