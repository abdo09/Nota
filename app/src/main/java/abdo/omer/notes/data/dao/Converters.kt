package abdo.omer.notes.data.dao

import abdo.omer.notes.data.models.Task
import abdo.omer.notes.data.models.TaskKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringToTasks(value: String): List<Task> {
        val listType = object : TypeToken<List<Task>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTasksToString(list: List<Task>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToTaskId(value: String): TaskKey {
        val listType = object : TypeToken<TaskKey>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTaskIdToString(list: TaskKey): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}