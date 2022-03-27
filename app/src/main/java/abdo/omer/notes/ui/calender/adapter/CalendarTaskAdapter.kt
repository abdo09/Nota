package abdo.omer.notes.ui.calender.adapter

import abdo.omer.notes.data.models.Task
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

    private var onTaskClickListener: ((Task) -> Unit)? = null

    override fun onBindViewHolder(holder: CalendarTaskViewHolder, position: Int) {

        val task = differ.currentList[position]

        holder.bind(task)

        holder.calendarTasksItemBinding.apply {
            this.root.setOnClickListener {
                onTaskClickListener?.let { it(task) }
            }
        }
    }

    fun setOnTaskClickListener(listener: (Task) -> Unit) {
        onTaskClickListener = listener
    }

}