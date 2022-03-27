package abdo.omer.notes.ui.calender

import abdo.omer.notes.base.BaseViewModel
import abdo.omer.notes.data.client.network.SingleLiveEvent
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.data.repository.roomRepository.TaskRoomRepository
import androidx.lifecycle.MutableLiveData

class CalendarViewModel(private val taskRoomRepository: TaskRoomRepository): BaseViewModel() {
    var taskList = SingleLiveEvent<List<Task>>()
    var goToPosition =  SingleLiveEvent<Int>()
    var day = -1

    fun getAllTasks(): List<Task>{
        val tasks =  taskRoomRepository.getAllTasksFromDB()
        if (tasks.isNotEmpty()) taskList.postValue(tasks)
        return tasks
    }
}