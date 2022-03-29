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

        }
    }

    fun setOnItemClickListener(listener: (Task) -> Unit) {
        onTaskClickListener = listener
    }

}