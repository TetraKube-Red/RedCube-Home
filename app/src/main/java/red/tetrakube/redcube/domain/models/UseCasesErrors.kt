package red.tetrakube.redcube.domain.models

import java.lang.Exception

sealed class UseCasesErrors() : Exception() {

    data object RemoteUnreachable : UseCasesErrors() {
        private fun readResolve(): Any = RemoteUnreachable
    }

    data object ServerError : UseCasesErrors() {
        private fun readResolve(): Any = ServerError
    }

    data object Unauthorized : UseCasesErrors() {
        private fun readResolve(): Any = Unauthorized
    }

    data object ApplicationError: UseCasesErrors() {
        private fun readResolve(): Any = ApplicationError
    }

}