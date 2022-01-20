package abdo.omer.notes.ui.addTask

import abdo.omer.notes.base.BaseViewModel
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.data.models.TaskKey
import abdo.omer.notes.data.repository.roomRepository.TaskRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTaskViewModel(private val taskRoomRepository: TaskRoomRepository): BaseViewModel() {

    var task = Task()

    fun upsertTask(task: Task){
        launch {
            withContext(Dispatchers.IO){taskRoomRepository.upsertTask(task)}
        }
    }

}