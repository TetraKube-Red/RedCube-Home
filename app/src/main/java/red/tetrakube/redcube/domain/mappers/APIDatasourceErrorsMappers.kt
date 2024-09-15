package red.tetrakube.redcube.domain.mappers

import red.tetrakube.redcube.data.api.dto.APIDatasourceErrors
import red.tetrakube.redcube.domain.models.UseCasesErrors

fun Throwable.toUseCasesErrors(): UseCasesErrors {
    return when(this) {
        is APIDatasourceErrors.ServerError -> UseCasesErrors.ServerError
        APIDatasourceErrors.RemoteUnreachable -> UseCasesErrors.RemoteUnreachable
        APIDatasourceErrors.Unauthorized -> UseCasesErrors.Unauthorized
        else -> UseCasesErrors.ApplicationError
    }
}