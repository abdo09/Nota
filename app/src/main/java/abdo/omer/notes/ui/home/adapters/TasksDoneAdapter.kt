package abdo.omer.notes.ui.home.adapters

import abdo.omer.notes.R
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.data.models.TaskKey
import abdo.omer.notes.databinding.TasksItemBinding
import abdo.omer.notes.utlis.drawable
import abdo.omer.notes.utlis.getCustomDrawable
import abdo.omer.notes.utlis.loadWithGlide
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TasksDoneAdapter : RecyclerView.Adapter<TasksDoneAdapter.TasksViewHolder>() {

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

    private var onItemCheckedListener: ((Task) -> Unit)? = null

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = differ.currentList[position]
        holder.bind(task)

        holder.taskItemBinding.apply {
            when (task.key) {
                TaskKey.SHOPPING -> {
                    taskIcon.context.apply {
                        loadWithGlide(
                            taskIcon,
                            this.getCustomDrawable(R.drawable.ic_shopping)
                        )
                    }
                    taskIndicator.context.apply {
                        loadWithGlide(
                            taskIndicator,
                            this.getCustomDrawable(R.drawable.ic_shopping_circle_indicator)
                        )
                    }
                }
                TaskKey.SPORTS -> {
                    taskIcon.context.apply {
                        loadWithGlide(
                            taskIcon,
                            this.getCustomDrawable(R.drawable.ic_sports)
                        )
                    }
                    taskIndicator.context.apply {
                        loadWithGlide(
                            taskIndicator,
                            this.getCustomDrawable(R.drawable.ic_sports_circle_indicator)
                        )
                    }
                }
                TaskKey.GOTO -> {
                    taskIcon.context.apply {
                        loadWithGlide(
                            taskIcon,
                            this.getCustomDrawable(R.drawable.ic_go_to)
                        )
                    }
                    taskIndicator.context.apply {
                        loadWithGlide(
                            taskIndicator,
                            this.getCustomDrawable(R.drawable.ic_go_to_circle_indicator)
                        )
                    }
                }
                TaskKey.EVENT -> {
                    taskIcon.context.apply {
                        loadWithGlide(
                            taskIcon,
                            this.getCustomDrawable(R.drawable.ic_event)
                        )
                    }
                    taskIndicator.context.apply {
                        loadWithGlide(
                            taskIndicator,
                            this.getCustomDrawable(R.drawable.ic_event_circle_indicator)
                        )
                    }
                }
                TaskKey.GYM -> {
                    taskIcon.context.apply {
                        loadWithGlide(
                            taskIcon,
                            this.getCustomDrawable(R.drawable.ic_gym)
                        )
                    }
                    taskIndicator.context.apply {
                        loadWithGlide(
                            taskIndicator,
                            this.getCustomDrawable(R.drawable.ic_gym_circle_indicator)
                        )
                    }
                }
                TaskKey.OTHERS -> {
                    taskIcon.context.apply {
                        loadWithGlide(
                            taskIcon,
                            this.getCustomDrawable(R.drawable.ic_others)
                        )
                    }
                    taskIndicator.context.apply {
                        loadWithGlide(
                            taskIndicator,
                            this.getCustomDrawable(R.drawable.ic_others_circle_indicator)
                        )
                    }
                }
            }

            tvTaskTitle.text = task.name

            tvDay.visibility = View.INVISIBLE
            tvTime.visibility = View.INVISIBLE
            checkbox.visibility = View.VISIBLE

            checkbox.apply {
                background = drawable(context,"#FFFFFF", 10, 10, 10, 10)
                elevation = 8f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    outlineAmbientShadowColor = Color.parseColor("#fe1e9a")
                    outlineSpotShadowColor = Color.parseColor("#fe1e9a")
                }

                if (!task.taskIsDone) {
                    this.setImageResource(0)
                } else {
                    this.setImageResource(R.drawable.ic_mark_done)
                }

                setOnClickListener {

                    if (task.taskIsDone) {
                        task.taskIsDone = !task.taskIsDone
                        this.setImageResource(0)
                    } else {
                        task.taskIsDone = !task.taskIsDone
                        this.setImageResource(R.drawable.ic_mark_done)
                    }
                }
            }
        }
    }

    fun setOnCheckedListener(listener: (Task) -> Unit) {
        onItemCheckedListener = listener
    }

}