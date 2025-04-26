package com.example.scrollblocker.utils

sealed class SupportedApps(val packageName: String, val blockId: String) {
	data object Instagram : SupportedApps("com.instagram.android", "com.instagram.android")
	data object Youtube : SupportedApps("com.google.android.youtube", "com.instagram.android")
	data object Linkedin : SupportedApps("com.linkedin.android", "com.instagram.android")
	data object Snapchat : SupportedApps("com.snapchat.android", "com.instagram.android")

	data object YoutubeRevanced :
		SupportedApps("app.revanced.android.youtube", "com.instagram.android")

	data object YoutubeRevancedExtended :
		SupportedApps("app.rvx.android.youtube", "com.instagram.android")

}