package com.example.scrollblocker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scrollblocker.presentation.home.HomeScreen
import com.example.scrollblocker.presentation.onboarding.PermissionScreen
import com.example.scrollblocker.presentation.onboarding.WelcomeScreen
import com.example.scrollblocker.presentation.viewmodels.MainViewModel
import com.example.scrollblocker.utils.isAccessibilityServiceEnabled


@Composable
fun NavGraph(viewModel: MainViewModel) {
	val context = LocalContext.current
	val navController = rememberNavController()

	val todayAppUsage by viewModel.todayAppUsageList.collectAsState(initial = emptyList())
	val isServiceEnabled by remember {
		mutableStateOf(isAccessibilityServiceEnabled(context))
	}

	val startRoute = if (isServiceEnabled) {
		Screen.HomeScreen.name
	} else {
		Screen.WelcomeScreen.name
	}

	NavHost(
		navController = navController, startDestination = startRoute
	) {
		composable(route = Screen.HomeScreen.name) {
			HomeScreen(
				todayAppUsage,
				appState = viewModel.appState,
				onMainEvent = viewModel::onEvent,
				onNavigate = { route ->
					navController.navigate(route = route)
				})
		}

		composable(route = Screen.WelcomeScreen.name) {
			WelcomeScreen(onNavigate = { route ->
				navController.navigate(route = route) {
					popUpTo(Screen.WelcomeScreen.name) {
						inclusive = true
					}
				}
			})
		}

		composable(route = Screen.PermissionScreen.name) {
			PermissionScreen(onNavigate = { route ->
				navController.navigate(route = route) {
					popUpTo(Screen.PermissionScreen.name) {
						inclusive = true
					}
				}
			})
		}
	}
}