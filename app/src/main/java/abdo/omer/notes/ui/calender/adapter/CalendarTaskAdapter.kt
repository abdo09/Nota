package abdo.omer.notes.ui.calender.adapter

import abdo.omer.notes.R
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.data.models.TaskKey
import abdo.omer.notes.databinding.CalendarTasksItemBinding
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CalendarTaskAdapter : RecyclerView.Adapter<CalendarTaskAdapter.CalendarTaskViewHolder>() {

    inner class CalendarTaskViewHolder(val calendarTasksItemBinding: CalendarTasksItemBinding) :
        RecyclerView.ViewHolder(calendarTasksItemBinding.root) {
        fun bind(task: Task) {
            calendarTasksItemBinding.task = task
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarTaskViewHolder {
        return CalendarTaskViewHolder(
            CalendarTasksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CalendarTaskViewHolder, position: Int) {
        val task = differ.currentList[position]
        holder.bind(task)

        holder.calendarTasksItemBinding.apply {
            when(task.key){
                TaskKey.SHOPPING -> {
                    taskIcon.setImageResource(R.drawable.ic_shopping)
                    taskIndicator.setImageResource(R.drawable.ic_shopping_circle_indicator)
                }
                TaskKey.SPORTS -> {
                    taskIcon.setImageResource(R.drawable.ic_sports)
                    taskIndicator.setImageResource(R.drawable.ic_sports_circle_indicator)
                }
                TaskKey.GOTO -> {
                    taskIcon.setImageResource(R.drawable.ic_go_to)
                    taskIndicator.setImageResource(R.drawable.ic_go_to_circle_indicator)
                }
                TaskKey.EVENT -> {
                    taskIcon.setImageResource(R.drawable.ic_event)
                    taskIndicator.setImageResource(R.drawable.ic_event_circle_indicator)
                }
                TaskKey.GYM -> {
                    taskIcon.setImageResource(R.drawable.ic_gym)
                    taskIndicator.setImageResource(R.drawable.ic_gym_circle_indicator)
                }
                TaskKey.OTHERS -> {
                    taskIcon.setImageResource(R.drawable.ic_others)
                    taskIndicator.setImageResource(R.drawable.ic_others_circle_indicator)
                }
            }

            val hour = task.time.hours!!
            val minute = task.time.minute!!
            var times = ""
            if (hour <= 9) {
                times = "0$hour:$minute"
            }
            if (minute <= 9) {
                times = "$hour:0$minute"
            }
            if (hour <= 9 && minute <= 9) {
                times = "0$hour:0$minute"
            }
            if (hour > 9 && minute > 9) {
                times = "$hour:$minute"
            }

            time.text = times
            tvTaskTitle.text = task.name

        }
    }
}