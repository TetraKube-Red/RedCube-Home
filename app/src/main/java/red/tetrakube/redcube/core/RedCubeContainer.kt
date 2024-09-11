package red.tetrakube.redcube.core

import red.tetrakube.redcube.data.datasource.db.HubDataSource
import red.tetrakube.redcube.data.datasource.db.RedCubeDatabase

class RedCubeContainer {

    private val redCubeDatabase by lazy { RedCubeDatabase() }

    val hubDataSource by lazy { HubDataSource(redCubeDatabase.db) }

}