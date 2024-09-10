package red.tetrakube.redcube.core

import android.content.Context
import red.tetrakube.redcube.data.datasource.db.HubDataSource
import red.tetrakube.redcube.data.datasource.db.RedCubeDatabase

class RedCubeContainer(context: Context) {

    private val redCubeDatabase = RedCubeDatabase()

    val hubDataSource by lazy { HubDataSource(redCubeDatabase.db) }

}