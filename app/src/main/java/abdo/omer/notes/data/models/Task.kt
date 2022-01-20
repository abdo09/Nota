package abdo.omer.notes.data.models

import abdo.omer.notes.data.models.Task.Companion.TABLE_NAME
import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@Entity(tableName = TABLE_NAME)
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var key: TaskKey = TaskKey.SHOPPING,
    var owner: String = "",
    var name: String = "",
    var description: String = "",
    @Embedded
    var day: TaskDate = TaskDate(),
    var time: String = "",
    var taskIsDone: Boolean = false
): Parcelable {
    @Parcelize
    data class TaskDate(
        var dayOfTheMonth: String = "",
        var dayOfTheWeek: String = "",
        var dayInLong: Long = 0L
    ): Parcelable

    companion object {
        const val TABLE_NAME = "task_table"
    }
}