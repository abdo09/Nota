package abdo.omer.notes.ui.addTask

import abdo.omer.notes.data.models.TaskKey
import abdo.omer.notes.databinding.IconItemBinding
import abdo.omer.notes.utlis.Constants
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class AddTaskIconAdapter(private val context: Context): RecyclerView.Adapter<AddTaskIconAdapter.AddTaskIconViewHolder>() {

    inner class AddTaskIconViewHolder(val iconItemBinding: IconItemBinding) :
        RecyclerView.ViewHolder(iconItemBinding.root) {
        fun bind(taskKey: TaskKey) {
            iconItemBinding.taskKey = taskKey
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<TaskKey>() {
        override fun areItemsTheSame(oldItem: TaskKey, newItem: TaskKey): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TaskKey, newItem: TaskKey): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTaskIconViewHolder {
        return AddTaskIconViewHolder(
            IconItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemCheckedListener: ((TaskKey) -> Unit)? = null

    override fun onBindViewHolder(holder: AddTaskIconViewHolder, position: Int) {
        val taskKey = differ.currentList[position]

        holder.iconItemBinding.apply {

            root.setOnClickListener {
                Constants().selectedIcon(context, taskKey.name)
                onItemCheckedListener?.let { it(taskKey) }
            }
        }

        holder.bind(taskKey)
    }

    fun setOnCheckedListener(listener: (TaskKey) -> Unit) {
        onItemCheckedListener = listener
    }
}