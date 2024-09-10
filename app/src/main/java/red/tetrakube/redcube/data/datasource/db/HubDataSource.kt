package red.tetrakube.redcube.data.datasource.db

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import red.tetrakube.redcube.data.datasource.db.entities.HubEntity

class HubDataSource(
    private val redCubeDB: Realm
) {

    fun getActiveHub() =
        redCubeDB.query(HubEntity::class, "active = true")
            .find()
            .firstOrNull()

    fun getActiveHubConnectivityInfo() {
        redCubeDB.query<HubEntity>("active = true")
    }

}