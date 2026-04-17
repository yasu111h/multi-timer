package com.multitimer.app.data.di

import android.content.Context
import androidx.room.Room
import com.multitimer.app.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "multitimer.db").build()

    @Provides fun provideTimerDao(db: AppDatabase) = db.timerDao()
    @Provides fun provideStopwatchDao(db: AppDatabase) = db.stopwatchDao()
    @Provides fun provideLapDao(db: AppDatabase) = db.lapDao()
    @Provides fun providePresetDao(db: AppDatabase) = db.presetDao()
}
