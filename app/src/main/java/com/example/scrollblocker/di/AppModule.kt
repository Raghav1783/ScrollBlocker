package com.example.scrollblocker.di

import android.content.Context
import androidx.room.Room
import com.example.scrollblocker.data.local.AppDatabase
import com.example.scrollblocker.data.local.AppUsageDao
import com.example.scrollblocker.data.local.MainRepository
import com.example.scrollblocker.data.local.SummaryDao
import com.example.scrollblocker.utils.SettingsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Provides
	@Singleton
	fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
		return Room.databaseBuilder(
			context.applicationContext, AppDatabase::class.java, "scrollblock_db"
		).build()
	}

	@Provides
	@Singleton
	fun provideAppUsageDao(database: AppDatabase): AppUsageDao {
		return database.appUsageDao()
	}

	@Provides
	@Singleton
	fun provideSummaryDao(database: AppDatabase): SummaryDao {
		return database.summaryDao()
	}

	@Provides
	@Singleton
	fun provideMainRepository(appUsageDao: AppUsageDao, summaryDao: SummaryDao): MainRepository {
		return MainRepository(appUsageDao, summaryDao)
	}

	@Provides
	@Singleton
	fun provideContext(@ApplicationContext context: Context): Context {
		return context
	}

	@Provides
	@Singleton
	fun provideSettingsStore(context: Context): SettingsStore {
		return SettingsStore(context)
	}
}
