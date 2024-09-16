package red.tetrakube.redcube.core

import android.content.Context
import red.tetrakube.redcube.data.api.TetraCubeAPIClient
import red.tetrakube.redcube.data.api.datasource.HubAPI
import red.tetrakube.redcube.data.db.RedCubeDatabase

class RedCubeContainer(context: Context) {

    private val tetraCubeAPIClient by lazy { TetraCubeAPIClient() }

    val redCubeDatabase by lazy { RedCubeDatabase.getInstance(context) }
    val hubAPI by lazy { HubAPI(tetraCubeAPIClient) }

}