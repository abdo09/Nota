package abdo.omer.notes.ui.completedTask

import abdo.omer.notes.base.BaseViewModel
import abdo.omer.notes.data.client.network.SingleLiveEvent
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.data.repository.roomRepository.TaskRoomRepository

class CompletedTaskViewModel(private val taskRoomRepository: TaskRoomRepository): BaseViewModel() {
    var taskList = SingleLiveEvent<List<Task>>()

    fun getAllTasks(): List<Task>{
        val tasks =  taskRoomRepository.getAllTasksFromDB()
        if (tasks.isNotEmpty()) taskList.postValue(tasks)
        return tasks
    }
}