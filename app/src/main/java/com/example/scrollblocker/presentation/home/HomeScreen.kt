package com.example.scrollblocker.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scrollblocker.R
import com.example.scrollblocker.domain.model.AppInfo
import com.example.scrollblocker.domain.model.AppUsage
import com.example.scrollblocker.domain.model.TimeWastedInfo
import com.example.scrollblocker.presentation.common.CustomPieChart
import com.example.scrollblocker.presentation.common.descriptionStyle
import com.example.scrollblocker.presentation.common.fontMontserrat
import com.example.scrollblocker.presentation.common.h2style
import com.example.scrollblocker.presentation.common.titleStyle
import com.example.scrollblocker.presentation.home.components.AppInfoComponent
import com.example.scrollblocker.presentation.home.components.NavigationDrawerComponent
import com.example.scrollblocker.presentation.home.components.PieChartIndicatorComponent
import com.example.scrollblocker.presentation.main.MainEvent
import com.example.scrollblocker.presentation.main.MainState
import com.example.scrollblocker.ui.theme.ScrollBlockTheme
import com.example.scrollblocker.ui.theme.black200
import com.example.scrollblocker.ui.theme.blackGradient
import com.example.scrollblocker.ui.theme.blue
import com.example.scrollblocker.ui.theme.pieChartColors
import com.example.scrollblocker.ui.theme.white
import com.example.scrollblocker.utils.formatTime
import com.example.scrollblocker.utils.getAppTimeSpent
import com.example.scrollblocker.utils.instagramPackage
import com.example.scrollblocker.utils.isAccessibilityServiceEnabled
import com.example.scrollblocker.utils.linkedinPackage
import com.example.scrollblocker.utils.openAccessibilitySettings
import com.example.scrollblocker.utils.snapchatPackage
import com.example.scrollblocker.utils.youtubePackage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
	todayAppUsage: List<AppUsage>,
	appState: MainState,
	onNavigate: (String) -> Unit,
	onMainEvent: (MainEvent) -> Unit
) {

	val context = LocalContext.current
	val scope = rememberCoroutineScope()

	//todo: refactor it later
	val isServiceEnabled by remember {
		mutableStateOf(isAccessibilityServiceEnabled(context))
	}

	// todo: only show today app usage instead of all
	val instagramTimeSpent = getAppTimeSpent(todayAppUsage, instagramPackage)
	val youtubeTimeSpent = getAppTimeSpent(todayAppUsage, youtubePackage)
	val linkedinTimeSpent = getAppTimeSpent(todayAppUsage, linkedinPackage)
	val snapchatTimeSpent = getAppTimeSpent(todayAppUsage, snapchatPackage)

	//    val groupedData = allAppUsage.groupBy { it.packageName }

	val timeWastedList = listOf(
		TimeWastedInfo("Instagram", instagramTimeSpent),
		TimeWastedInfo("Youtube", youtubeTimeSpent),
		TimeWastedInfo("Linkedin", linkedinTimeSpent),
		TimeWastedInfo("Snapchat", snapchatTimeSpent)
	)

	val totalTimeWasted = timeWastedList.filter { it.timeWasted > 0 }.map { it.timeWasted }

	val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

	ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
		ModalDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.primary) {
			NavigationDrawerComponent(onMainEvent)
		}
	}) {
		Scaffold(topBar = {
			TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
				title = {
					Text(
						text = stringResource(id = R.string.app_name), style = h2style
					)
				},
				navigationIcon = {
					IconButton(onClick = {
						scope.launch {
							drawerState.apply {
								if (isClosed) open() else close()
							}
						}
					}) {
						Icon(
							imageVector = Icons.Default.Menu, contentDescription = null
						)
					}
				})
		}) { innerPadding ->
			Column(
				modifier = Modifier
					.fillMaxSize()
					.background(MaterialTheme.colorScheme.primary)
					.padding(innerPadding),
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.height(350.dp)
				) {

					Column(
						modifier = Modifier
							.fillMaxWidth()
							.height(327.dp)
							.clip(
								RoundedCornerShape(
									bottomStart = 24.dp, bottomEnd = 24.dp
								)
							)
							.background(blackGradient),
						horizontalAlignment = Alignment.CenterHorizontally,
					) {
						// pie chart
						if (totalTimeWasted.sum() != 0) {
							Box(
								modifier = Modifier
									.clip(CircleShape)
									.clickable {                                    // todo: navigate to analytics screen
									}, contentAlignment = Alignment.Center
							) {
								Column(
									modifier = Modifier.padding(top = 8.dp),
									horizontalAlignment = Alignment.CenterHorizontally
								) {
									Text(
										text = "Time Wasted",
										textAlign = TextAlign.Center,
										style = descriptionStyle
									)
									Text(
										text = formatTime(totalTimeWasted.sum()),
										textAlign = TextAlign.Center,
										fontSize = 28.sp,
										fontFamily = fontMontserrat,
									)
								}
								CustomPieChart(
									data = totalTimeWasted, pieChartSize = 175.dp
								)
							}
						} else {
							Box(
								modifier = Modifier.fillMaxWidth(),
								contentAlignment = Alignment.Center
							) {
								Image(
									painter = painterResource(R.drawable.empty),
									contentDescription = null
								)
								Box(
									modifier = Modifier
										.fillMaxWidth()
										.height(305.dp)
										.clip(RoundedCornerShape(24.dp))
										.background(
											Brush.verticalGradient(
												listOf(
													Color.Transparent, black200
												)
											)
										)
								)
								Text(
									text = "No Activity", style = titleStyle, color = white
								)
							}
						}

						// pie chart indicator
						LazyVerticalGrid(
							modifier = Modifier
								.fillMaxWidth()
								.padding(horizontal = 32.dp),
							columns = GridCells.Fixed(2),
							verticalArrangement = Arrangement.spacedBy(8.dp),
							horizontalArrangement = Arrangement.spacedBy(24.dp),
						) {
							items(timeWastedList) {
								PieChartIndicatorComponent(
									appName = it.name,
									time = it.timeWasted,
									color = pieChartColors[timeWastedList.indexOf(it) % pieChartColors.size]
								)
							}
						}
					}

					Button(
						modifier = Modifier.align(Alignment.BottomCenter), onClick = {
							openAccessibilitySettings(context)
						}, colors = ButtonDefaults.buttonColors(
							containerColor = blue,
							contentColor = MaterialTheme.colorScheme.onPrimary
						)
					) {
						Text(
							modifier = Modifier.padding(
								horizontal = 7.dp, vertical = 4.dp
							),
							text = if (isServiceEnabled) "Stop Service" else "Start Service",
							fontWeight = FontWeight.Bold,
							fontSize = 16.sp
						)
					}
				}

				// supported apps list
				Text(
					modifier = Modifier.padding(
						16.dp, 16.dp, 16.dp, 0.dp
					), text = "Block apps", style = h2style, fontWeight = FontWeight.Bold
				)
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 16.dp),
					verticalArrangement = Arrangement.spacedBy(10.dp)
				) {
					// todo: refactor it later
					val supportedApps = listOf(
						AppInfo(
							R.drawable.instagram, "Instagram Reels", appState.instagramKey
						),
						AppInfo(
							R.drawable.youtube, "Youtube Shorts", appState.youtubeKey
						),
						AppInfo(
							R.drawable.linkedin, "Linkedin Video", appState.linkedinKey
						),
						AppInfo(
							R.drawable.snapchat, "Snapchat Spotlight", appState.snapchatKey
						),
					)

					supportedApps.forEach {
						AppInfoComponent(
							app = it, index = supportedApps.indexOf(it)
						) {
							scope.launch {
								when (it.name) {
									"Instagram Reels" -> {
										onMainEvent(MainEvent.OnToggleInstagram(!appState.instagramKey))
									}

									"Youtube Shorts" -> {
										onMainEvent(MainEvent.OnToggleYoutube(!appState.youtubeKey))
									}

									"Linkedin Video" -> {
										onMainEvent(MainEvent.OnToggleLinkedin(!appState.linkedinKey))
									}

									"Snapchat Spotlight" -> {
										onMainEvent(MainEvent.OnToggleSnapchat(!appState.snapchatKey))
									}
								}
							}
						}
					}
				}
			}
		}
	}
}

@Preview
@Composable
private fun HomeScreenPreview() {
	ScrollBlockTheme {        //create a fake appUsageList
		val appUsageList = listOf(
			AppUsage(
				packageName = "com.instagram.android", timeSpent = 11020
			),
			AppUsage(
				packageName = "com.snapchat.android", timeSpent = 1210
			),
		)
		HomeScreen(appUsageList, MainState(), {}, {})
	}
}