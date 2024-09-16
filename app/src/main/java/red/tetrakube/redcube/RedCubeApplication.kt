package red.tetrakube.redcube

import android.app.Application
import red.tetrakube.redcube.core.RedCubeContainer

class RedCubeApplication : Application() {
    val appContainer = RedCubeContainer(this)
}