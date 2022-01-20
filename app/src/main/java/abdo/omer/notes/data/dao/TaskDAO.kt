package abdo.omer.notes.data.dao

import abdo.omer.notes.data.models.Task
import androidx.room.*

@Dao
abstract class TaskDAO : BaseDao<Task> {

    @Query("select * from ${Task.TABLE_NAME}")
    abstract fun getAllTasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsert(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsertAll(tasks: List<Task>)

}