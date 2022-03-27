package abdo.omer.notes.data

import abdo.omer.notes.data.dao.Converters
import abdo.omer.notes.data.dao.TaskDAO
import abdo.omer.notes.data.models.Task
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Task::class
    ], version = 2, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TasksDB : RoomDatabase() {
    abstract fun taskDao(): TaskDAO
}