package com.example.notetakingapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.example.notetakingapp.data.Note
import com.example.notetakingapp.data.NoteDao
import com.example.notetakingapp.data.NoteListDatabase
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var noteDao: NoteDao
    private lateinit var noteListDatabase: NoteListDatabase
    private val note1 = Note(1, "Test1", "Working", 0)
    private val note2 = Note(2, "Test2", "Working", 1)


    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        noteListDatabase = Room.inMemoryDatabaseBuilder(context, NoteListDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        noteDao = noteListDatabase.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        noteListDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsNoteIntoDB() = runBlocking {
        addOneNoteToDb()
        val allItems = noteDao.getAllNotes().first()
        assertEquals(allItems[0], note1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllNotes_returnsAllNotesFromDB() = runBlocking {
        addTwoNotesToDb()
        val allNotes = noteDao.getAllNotes().first()
        assertEquals(allNotes[0], note1)
        assertEquals(allNotes[1], note2)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetNote_returnsNoteFromDB() = runBlocking {
        addOneNoteToDb()
        val item = noteDao.getNotes(1)
        assertEquals(item.first(), note1)
    }


    @Test
    @Throws(Exception::class)
    fun daoDeleteNotes_deletesAllNotesFromDB() = runBlocking {
        addTwoNotesToDb()
        noteDao.delete(note1)
        noteDao.delete(note2)
        val allNotes = noteDao.getAllNotes().first()
        assertTrue(allNotes.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateNotes_updatesNoteInDB() = runBlocking {
        addTwoNotesToDb()
        noteDao.update(Note(1, "Apples", "Yum", 25))
        noteDao.update(Note(2, "Bananas", "mum", 50))

        val allNotes = noteDao.getAllNotes().first()
        assertEquals(allNotes[0], Note(1, "Apples", "Yum", 25))
        assertEquals(allNotes[1], Note(2, "Bananas", "mum", 50))
    }

    private suspend fun addOneNoteToDb() {
        noteDao.insert(note1)
    }

    private suspend fun addTwoNotesToDb() {
        noteDao.insert(note1)
        noteDao.insert(note2)
    }
}
