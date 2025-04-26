package com.example.scrollblocker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.scrollblocker.domain.model.Summary

@Dao
interface SummaryDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertSummary(summary: Summary)

	@Query("SELECT * FROM Summary")
	suspend fun getSummary(): Summary

	@Update
	suspend fun updateSummary(summary: Summary)
}