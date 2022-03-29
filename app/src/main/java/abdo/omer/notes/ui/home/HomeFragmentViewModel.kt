package abdo.omer.notes.ui.home

import abdo.omer.notes.base.BaseViewModel
import abdo.omer.notes.data.client.network.SingleLiveEvent
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.data.models.TaskKey
import abdo.omer.notes.data.repository.roomRepository.TaskRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragmentViewModel(private val taskRoomRepository: TaskRoomRepository): BaseViewModel() {

    var taskList = SingleLiveEvent<List<Task>>()
    var isDone = false

    suspend fun upsertTask(task: Task){
        taskRoomRepository.upsertTask(task)
    }

    fun getAllTasks(): List<Task>{
        val tasks =  taskRoomRepository.getAllTasksFromDB()
        if (tasks.isNotEmpty()) taskList.postValue(tasks)
        return tasks
    }

}