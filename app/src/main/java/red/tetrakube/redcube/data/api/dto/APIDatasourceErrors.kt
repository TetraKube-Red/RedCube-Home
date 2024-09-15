package red.tetrakube.redcube.data.api.dto

import java.lang.Exception

sealed class APIDatasourceErrors : Exception() {

    data object RemoteUnreachable : APIDatasourceErrors() {
        private fun readResolve(): Any = RemoteUnreachable
    }

    data object ServerError : APIDatasourceErrors() {
        private fun readResolve(): Any = ServerError
    }

    data object Unauthorized : APIDatasourceErrors() {
        private fun readResolve(): Any = Unauthorized
    }

    data object UnexpectedNullData : APIDatasourceErrors() {
        private fun readResolve(): Any = UnexpectedNullData
    }

}