package red.tetrakube.redcube.core

import red.tetrakube.redcube.data.api.TetraCubeAPIClient
import red.tetrakube.redcube.data.api.datasource.HubAPI
import red.tetrakube.redcube.data.db.datasource.HubDataSource
import red.tetrakube.redcube.data.db.RedCubeDatabase

class RedCubeContainer {

    private val redCubeDatabase by lazy { RedCubeDatabase() }
    private val tetraCubeAPIClient by lazy { TetraCubeAPIClient() }

    val hubDataSource by lazy { HubDataSource(redCubeDatabase.db) }
    val hubAPI by lazy { HubAPI(tetraCubeAPIClient) }

}