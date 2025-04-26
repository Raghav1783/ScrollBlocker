package com.example.scrollblocker.data.local

import com.example.scrollblocker.domain.model.AppUsage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
	private val appUsageDao: AppUsageDao, private val summaryDao: SummaryDao
) {

	suspend fun insertAppUsage(appUsage: AppUsage) {
		appUsageDao.insertAppUsage(appUsage)
	}

	fun getAllAppUsage(): Flow<List<AppUsage>> {
		return appUsageDao.getAllAppUsage()
	}

	fun getAppUsageByDate(date: String): Flow<List<AppUsage>> {
		return appUsageDao.getAppUsageByDate(date)
	}

	fun getAppUsageByPackageName(packageName: String): Flow<List<AppUsage>> {
		return appUsageDao.getAppUsageByPackageName(packageName)
	}

}