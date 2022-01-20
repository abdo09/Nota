package abdo.omer.notes.data.repository.roomRepository

import abdo.omer.notes.data.dao.TaskDAO
import abdo.omer.notes.data.models.Task


interface TaskRoomRepository {
    fun getAllTasksFromDB(): List<Task>
    suspend fun upsertTask(task: Task)
    suspend fun upsertAllTasks(tasks: List<Task>)
    suspend fun addAllTasks(tasks: List<Task>)

}

class TaskRoomRepositoryImp(
    private val taskDAO: TaskDAO
) : TaskRoomRepository {
    override fun getAllTasksFromDB(): List<Task> {
        return taskDAO.getAllTasks()
    }

    override suspend fun upsertTask(task: Task) {
        taskDAO.upsert(task)
    }

    override suspend fun upsertAllTasks(tasks: List<Task>) {
        taskDAO.upsertAll(tasks)
    }

    override suspend  fun addAllTasks(tasks: List<Task>) {
        taskDAO.insertAll(tasks)
    }
}