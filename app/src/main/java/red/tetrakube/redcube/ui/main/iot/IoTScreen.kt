package red.tetrakube.redcube.ui.main.iot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.launch
import red.tetrakube.redcube.R
import red.tetrakube.redcube.domain.models.MinimalActiveHub
import red.tetrakube.redcube.ui.main.iot.models.IoTScreenState
import red.tetrakube.redcube.ui.main.iot.models.TabItem

@Composable
fun IoTScreen(iotViewModel: IoTViewModel) {
    val screenState = iotViewModel.screenState.value
    val currentHub = remember(screenState) {
        derivedStateOf {
            if (screenState is IoTScreenState.HubLoaded) {
                screenState.minimalActiveHub
            } else {
                null
            }
        }
    }

    LaunchedEffect(Unit) {
        iotViewModel.getHubData()
    }

    IoTScreenUI(screenState, currentHub.value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IoTScreenUI(
    screenState: IoTScreenState,
    currentHub: MinimalActiveHub?
) {
    val tabs = arrayListOf(
        /*TabItem(
            title = "Switches",
            icon = {
                Icon(painterResource(R.drawable.switch_24px), null)
            }
        ),

        TabItem(
            title = "RGB Light",
            icon = {
                Icon(painterResource(R.drawable.emoji_objects_24px), null)
            }
        ),*/
        TabItem(
            title = "UPS",
            icon = {
                Icon(painterResource(R.drawable.battery_charging_80_24px), null)
            }
        ),

      /*  TabItem(
            title = "Power",
            icon = {
                Icon(painterResource(R.drawable.bolt_24px), null)
            }
        ),

        TabItem(
            title = "Network",
            icon = {
                Icon(painterResource(R.drawable.wifi_24px), null)
            }
        )*/
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })
    val viewScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(currentHub?.name ?: "Loading")
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = title.icon,
                        label = { Text(title.title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            viewScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (screenState is IoTScreenState.LoadingHub) {
                LinearProgressIndicator()
            }
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth()
            ) {

            }
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = index.toString())
                }
            }
        }
    }
}