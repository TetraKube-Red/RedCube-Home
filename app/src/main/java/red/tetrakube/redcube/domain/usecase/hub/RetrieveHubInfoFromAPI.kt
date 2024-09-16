package red.tetrakube.redcube.domain.usecase.hub

import red.tetrakube.redcube.data.api.datasource.HubAPI
import red.tetrakube.redcube.data.api.dto.APIDatasourceErrors
import red.tetrakube.redcube.data.api.mappers.toEntity
import red.tetrakube.redcube.data.db.datasource.HubDao
import red.tetrakube.redcube.domain.mappers.toMinimalActiveHub
import red.tetrakube.redcube.domain.mappers.toUseCasesErrors
import red.tetrakube.redcube.domain.models.MinimalActiveHub

class RetrieveHubInfoFromAPI(
    private val hubDataSource: HubDao,
    private val hubAPI: HubAPI
) {

    suspend operator fun invoke(
        apiURI: String,
        websocketURI: String,
        token: String
    ): Result<MinimalActiveHub> {
        val hubInfoResult = hubAPI.getHubInfo(apiURI, token)
        if (hubInfoResult.isFailure) {
            return Result.failure(hubInfoResult.exceptionOrNull()?.toUseCasesErrors()!!)
        }
        val hubInfo = hubInfoResult.getOrNull()
            ?: return Result.failure(APIDatasourceErrors.UnexpectedNullData)
        val hub = hubInfo.toEntity(true, apiURI, websocketURI, token)
        val minimalActiveHub = hubDataSource.insert(hub)
        return Result.success(hub.toMinimalActiveHub())
    }

}