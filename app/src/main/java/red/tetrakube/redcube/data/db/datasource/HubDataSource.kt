package red.tetrakube.redcube.data.db.datasource

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.flow
import red.tetrakube.redcube.data.api.dto.hub.HubInfo
import red.tetrakube.redcube.data.api.mappers.toEntity
import red.tetrakube.redcube.data.db.entities.HubEntity
import red.tetrakube.redcube.domain.mappers.toMinimalActiveHub
import red.tetrakube.redcube.domain.models.MinimalActiveHub

class HubDataSource(
    private val redCubeDB: Realm
) {

    fun getActiveHub() =
        redCubeDB.query(HubEntity::class, "active = true")
            .find()
            .firstOrNull()

    suspend fun streamActiveHub() = flow {
        redCubeDB.query(HubEntity::class, "active = true")
            .asFlow()
            .collect { changes: ResultsChange<HubEntity> ->
                when (changes) {
                    is UpdatedResults -> {
                        if (changes.list.size == 1) {
                            emit(changes.list.first())
                        }
                    }
                    else -> {}
                }
            }
    }

    fun getActiveHubConnectivityInfo() {
        redCubeDB.query<HubEntity>("active = true")
    }

    suspend fun storeHub(
        hubInfo: HubInfo,
        setAsDefault: Boolean = true,
        apiURI: String,
        websocketURI: String,
        token: String
    ): MinimalActiveHub? {
        return redCubeDB.write {
            val hub = hubInfo.toEntity(setAsDefault, apiURI, websocketURI, token)
            copyToRealm(hub)
        }
            .toMinimalActiveHub()
    }

}