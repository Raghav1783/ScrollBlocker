package com.example.scrollblocker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.scrollblocker.domain.model.AppUsage
import com.example.scrollblocker.domain.model.Summary

@Database(entities = [AppUsage::class, Summary::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
	abstract fun appUsageDao(): AppUsageDao
	abstract fun summaryDao(): SummaryDao
}