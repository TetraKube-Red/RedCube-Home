package red.tetrakube.redcube.data.datasource.db.entities

import io.realm.kotlin.types.EmbeddedRealmObject

class HubConnectivityEmbedded : EmbeddedRealmObject {

    var token: String = ""
    var apiURI: String = ""
    var websocketURI: String = ""

}