package com.example.scrollblocker.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.scrollblocker.presentation.common.smallDescriptionStyle
import com.example.scrollblocker.utils.formatTime

@Composable
fun PieChartIndicatorComponent(appName: String, time: Int, color: Color) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		Box(
			modifier = Modifier
				.size(16.dp)
				.background(color, CircleShape)
		)
		Text(modifier = Modifier.weight(1f), text = appName, style = smallDescriptionStyle)
		Text(formatTime(time), style = smallDescriptionStyle)
	}

}