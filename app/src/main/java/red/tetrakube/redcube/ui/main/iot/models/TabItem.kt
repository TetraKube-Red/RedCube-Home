package red.tetrakube.redcube.ui.main.iot.models

import androidx.compose.runtime.Composable

data class TabItem (
    val icon: @Composable () -> Unit,
    val title: String
)