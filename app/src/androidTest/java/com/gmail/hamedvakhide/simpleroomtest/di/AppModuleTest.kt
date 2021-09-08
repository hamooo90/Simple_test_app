package com.gmail.hamedvakhide.simpleroomtest.di

import android.content.Context
import androidx.room.Room
import com.gmail.hamedvakhide.simpleroomtest.data.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModuleTest {

    @Provides
    @Named("test_db")
    fun provideNoteDatabaseInMemory(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(context,NotesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

}