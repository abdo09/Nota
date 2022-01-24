package abdo.omer.notes.ui.home.adapters

import abdo.omer.notes.R
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.data.models.TaskKey
import abdo.omer.notes.databinding.TasksItemBinding
import abdo.omer.notes.utlis.getCustomDrawable
import abdo.omer.notes.utlis.loadWithGlide
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    inner class TasksViewHolder(val taskItemBinding: TasksItemBinding) :
        RecyclerView.ViewHolder(taskItemBinding.root) {
        fun bind(task: Task) {
            taskItemBinding.task = task
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            TasksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onTaskClickListener: ((Task) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = differ.currentList[position]
        holder.bind(task)

        holder.taskItemBinding.apply {

            this.root.setOnClickListener {
                onTaskClickListener?.let { it(task) }
            }

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
            var time = ""
            if (hour <= 9) {
                time = "0$hour:$minute"
            }
            if (minute <= 9) {
                time = "$hour:0$minute"
            }
            if (hour <= 9 && minute <= 9) {
                time = "0$hour:0$minute"
            }
            if (hour > 9 && minute > 9) {
                time = "$hour:$minute"
            }
            tvTaskTitle.text = task.name
            tvDay.text = "${task.day.dayOfTheMonth} ${task.day.dayOfTheWeek}"
            tvTime.text = time
            tvDay.visibility = View.VISIBLE
            tvTime.visibility = View.VISIBLE
            checkbox.visibility = View.INVISIBLE

        }
    }

    fun setOnItemClickListener(listener: (Task) -> Unit) {
        onTaskClickListener = listener
    }

}