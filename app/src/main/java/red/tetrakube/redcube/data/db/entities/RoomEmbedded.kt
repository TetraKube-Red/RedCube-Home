package red.tetrakube.redcube.data.db.entities

import io.realm.kotlin.types.EmbeddedRealmObject

class RoomEmbedded : EmbeddedRealmObject {

    var slug: String = ""
    var name: String = ""

}