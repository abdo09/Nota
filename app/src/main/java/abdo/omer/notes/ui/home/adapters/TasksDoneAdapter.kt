package abdo.omer.notes.ui.home.adapters

import abdo.omer.notes.R
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.databinding.TasksItemBinding
import abdo.omer.notes.utlis.drawable
import abdo.omer.notes.utlis.getCustomDrawable
import abdo.omer.notes.utlis.loadWithGlide
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TasksDoneAdapter : RecyclerView.Adapter<TasksDoneAdapter.TasksViewHolder>() {

    inner class TasksViewHolder(val taskItemBinding: TasksItemBinding) :
        RecyclerView.ViewHolder(taskItemBinding.root) {
        fun bind(task: Task, tasksDoneAdapter: TasksDoneAdapter) {
            taskItemBinding.task = task
            taskItemBinding.callBack = tasksDoneAdapter
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

    var onItemCheckedListener: ((Task) -> Unit)? = null

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = differ.currentList[position]
        holder.bind(task, this)

        holder.taskItemBinding.apply {

            tvDay.visibility = View.INVISIBLE
            tvTime.visibility = View.INVISIBLE
            checkboxDone.visibility = View.VISIBLE

            checkboxDone.apply {
                background = drawable(context,"#FFFFFF", 10, 10, 10, 10)
                elevation = 8f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    outlineAmbientShadowColor = Color.parseColor("#fe1e9a")
                    outlineSpotShadowColor = Color.parseColor("#fe1e9a")
                }
            }
        }
    }

    fun onCheckBoxClick(view: View, task: Task) {
        val checkBox = view.findViewById<ImageView>(R.id.checkbox_done)
        if (task.taskIsDone) {
            task.taskIsDone = !task.taskIsDone
            checkBox.setImageResource(0)
        } else {
            task.taskIsDone = !task.taskIsDone
            checkBox.setImageResource(R.drawable.ic_mark_done)
        }
        onItemCheckedListener?.let { it(task) }
    }

    fun setOnCheckedListener(listener: (Task) -> Unit) {
        onItemCheckedListener = listener
    }

}