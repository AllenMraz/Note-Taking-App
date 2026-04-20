package com.example.notetakingapp.data

import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasksStream(): Flow<List<Task>>

    fun getTaskStream(id: Int): Flow<Task?>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateItem(task: Task)
}