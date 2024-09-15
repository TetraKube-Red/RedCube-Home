package red.tetrakube.redcube.data.api.datasource

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import red.tetrakube.redcube.data.api.dto.APIDatasourceErrors
import red.tetrakube.redcube.data.api.TetraCubeAPIClient
import red.tetrakube.redcube.data.api.dto.hub.HubInfo

class HubAPI(
    private val tetraCubeAPIClient: TetraCubeAPIClient
) {

    companion object {
        const val GET_HUB_INFO_URL = "/iot/hubs/info"
    }

    suspend fun getHubInfo(apiURL: String, authToken: String): Result<HubInfo> {
        val requestResult = try {
            tetraCubeAPIClient.client.get("$apiURL$GET_HUB_INFO_URL")
            {
                headers {
                    append("Authorization", "Bearer $authToken")
                }
            }.body() as HubInfo
        } catch (clientException: ClientRequestException) {
            APIDatasourceErrors.Unauthorized
        } catch (serverException: ServerResponseException) {
            APIDatasourceErrors.ServerError
        } catch (connectionException: Exception) {
            APIDatasourceErrors.RemoteUnreachable
        }

        return if (requestResult is APIDatasourceErrors) {
            Result.failure(requestResult)
        } else {
            Result.success(requestResult as HubInfo)
        }
    }

}