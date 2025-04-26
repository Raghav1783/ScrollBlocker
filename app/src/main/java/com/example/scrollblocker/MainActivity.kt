package com.example.scrollblocker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.scrollblocker.presentation.navigation.NavGraph
import com.example.scrollblocker.presentation.viewmodels.MainViewModel
import com.example.scrollblocker.ui.theme.ScrollBlockTheme
import com.example.scrollblocker.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	private val viewModel by viewModels<MainViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()

		// create notification channel
		NotificationHelper(applicationContext).createNotificationChannel()

		// load app status
		viewModel.loadAppStatus()

		setContent {
			ScrollBlockTheme {
				NavGraph(viewModel)
			}
		}
	}
}
