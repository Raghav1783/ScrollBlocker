package com.example.scrollblocker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.scrollblocker.domain.model.AppUsage
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAppUsage(appUsage: AppUsage)

	@Query("SELECT * FROM AppUsage WHERE packageName = :appPackageName")
	fun getAppUsageByPackageName(appPackageName: String): Flow<List<AppUsage>>

	@Query("SELECT * FROM AppUsage")
	fun getAllAppUsage(): Flow<List<AppUsage>>

	@Query("SELECT * FROM AppUsage WHERE date = :date")
	fun getAppUsageByDate(date: String): Flow<List<AppUsage>>
}
