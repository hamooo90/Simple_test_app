package com.gmail.hamedvakhide.simpleroomtest.di

import android.content.Context
import androidx.room.Room
import com.gmail.hamedvakhide.simpleroomtest.adapter.NoteAdapter
import com.gmail.hamedvakhide.simpleroomtest.data.NoteDao
import com.gmail.hamedvakhide.simpleroomtest.data.NotesDatabase
import com.gmail.hamedvakhide.simpleroomtest.repositories.DefaultNoteRepository
import com.gmail.hamedvakhide.simpleroomtest.repositories.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNotesDatabase(
        @ApplicationContext context: Context
    ): NotesDatabase{
        return Room.databaseBuilder(context,NotesDatabase::class.java,"notes")
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(
        notesDatabase: NotesDatabase
    ): NoteDao{
        return notesDatabase.noteDao()
    }

    @Singleton
    @Provides
    fun provideDefaultRepository(
        dao: NoteDao
    ) = DefaultNoteRepository(dao) as NotesRepository

}