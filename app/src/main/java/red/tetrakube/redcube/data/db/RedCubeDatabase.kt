package red.tetrakube.redcube.data.db

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import red.tetrakube.redcube.data.db.entities.HubConnectivityEmbedded
import red.tetrakube.redcube.data.db.entities.HubEntity
import red.tetrakube.redcube.data.db.entities.RoomEmbedded

class RedCubeDatabase {

    private val config = RealmConfiguration.create(
        schema = setOf(
            HubConnectivityEmbedded::class,
            HubEntity::class,
            RoomEmbedded::class
        )
    )
    val db = Realm.open(config)

    init {
        Log.v("RedCubeDatabase", "Successfully opened realm: ${db.configuration.name}")
    }

}