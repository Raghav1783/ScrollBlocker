package com.example.scrollblocker.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SettingsStore @Inject constructor(private val context: Context) {

	companion object {
		val Context.dataStore: DataStore<Preferences> by preferencesDataStore("scrollblock-settings")

		private val INSTAGRAM_KEY = booleanPreferencesKey("instagram_key")
		private val YOUTUBE_KEY = booleanPreferencesKey("youtube_key")
		private val SNAPCHAT_KEY = booleanPreferencesKey("snapchat_key")
		private val LINKEDIN_KEY = booleanPreferencesKey("linkedin_key")
	}

	val instagramKey: Flow<Boolean> = context.dataStore.data.map { preferences ->
		preferences[INSTAGRAM_KEY] ?: true
	}

	val youtubeKey: Flow<Boolean> = context.dataStore.data.map { preferences ->
		preferences[YOUTUBE_KEY] ?: true
	}

	val snapchatKey: Flow<Boolean> = context.dataStore.data.map { preferences ->
		preferences[SNAPCHAT_KEY] ?: true
	}

	val linkedinKey: Flow<Boolean> = context.dataStore.data.map { preferences ->
		preferences[LINKEDIN_KEY] ?: true
	}

	suspend fun setInstagramKey(isEnabled: Boolean) {
		context.dataStore.edit { preferences ->
			preferences[INSTAGRAM_KEY] = isEnabled
		}
	}

	suspend fun setYoutubeKey(isEnabled: Boolean) {
		context.dataStore.edit { preferences ->
			preferences[YOUTUBE_KEY] = isEnabled
		}
	}

	suspend fun setLinkedinKey(isEnabled: Boolean) {
		context.dataStore.edit { preferences ->
			preferences[LINKEDIN_KEY] = isEnabled
		}
	}

	suspend fun setSnapchatKey(isEnabled: Boolean) {
		context.dataStore.edit { preferences ->
			preferences[SNAPCHAT_KEY] = isEnabled
		}
	}

}