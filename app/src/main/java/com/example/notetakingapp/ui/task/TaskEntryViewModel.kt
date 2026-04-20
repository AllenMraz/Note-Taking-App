package com.example.notetakingapp.ui.task


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.notetakingapp.data.Task
import com.example.notetakingapp.data.TaskRepository


class TaskEntryViewModel(private val taskRepository: TaskRepository): ViewModel(){

    var taskUiState by mutableStateOf(TaskUiState())
        private set

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState =
            TaskUiState(taskDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }
    suspend fun saveTask() {
        if (validateInput()){
            taskRepository.insertTask((taskUiState.taskDetails.toTask()))
        }
    }

    private  fun validateInput(uiState: TaskDetails = taskUiState.taskDetails): Boolean{
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank() && timestamp != 0
        }
    }
}


data class TaskUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isEntryValid: Boolean = false
)
data class TaskDetails(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val  timestamp: Int = 0
)

fun TaskDetails.toTask(): Task = Task(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp
)

fun Task.toTaskUiState(isEntryValid: Boolean = false): TaskUiState = TaskUiState(
    taskDetails =  this.toTaskDetails(),
    isEntryValid = isEntryValid
)

fun Task.toTaskDetails(): TaskDetails = TaskDetails(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp
)
