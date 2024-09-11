package red.tetrakube.redcube.data.datasource.db.entities

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class HubEntity : RealmObject {

    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var slug: String = ""
    var name: String = ""
    var active: Boolean = false
    var connectivity: HubConnectivityEmbedded? = null
    var rooms: RealmList<RoomEmbedded> = realmListOf()

}