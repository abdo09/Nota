package abdo.omer.notes.ui.addTask

import abdo.omer.notes.R
import abdo.omer.notes.data.models.TaskKey
import abdo.omer.notes.databinding.IconItemBinding
import abdo.omer.notes.utlis.Constants
import abdo.omer.notes.utlis.getCustomDrawable
import abdo.omer.notes.utlis.loadWithGlide
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class AddTaskIconAdapter(private val context: Context): RecyclerView.Adapter<AddTaskIconAdapter.AddTaskIconViewHolder>() {

    inner class AddTaskIconViewHolder(val iconItemBinding: IconItemBinding) :
        RecyclerView.ViewHolder(iconItemBinding.root) {
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
            val selectedIcon = Constants().selectedIcon(context = context)
            when(taskKey.name){
                TaskKey.SHOPPING.name -> {
                    icon.setImageResource(R.drawable.ic_shopping)
                    if (selectedIcon == taskKey.name){
                        iconIndicator.setImageResource(R.drawable.ic_shopping_circle_indicator)
                    }else{
                        iconIndicator.setImageResource(0)
                    }
                }
                TaskKey.SPORTS.name -> {
                    icon.setImageResource(R.drawable.ic_sports)
                    if (selectedIcon == taskKey.name){
                        iconIndicator.setImageResource(R.drawable.ic_sports_circle_indicator)
                    }else{
                        iconIndicator.setImageResource(0)
                    }
                }
                TaskKey.GOTO.name -> {
                    icon.setImageResource(R.drawable.ic_go_to)
                    if (selectedIcon == taskKey.name){
                        iconIndicator.setImageResource(R.drawable.ic_go_to_circle_indicator)
                    }else{
                        iconIndicator.setImageResource(0)
                    }
                }
                TaskKey.EVENT.name -> {
                    icon.setImageResource(R.drawable.ic_event)
                    if (selectedIcon == taskKey.name){
                        iconIndicator.setImageResource(R.drawable.ic_event_circle_indicator)
                    }else{
                        iconIndicator.setImageResource(0)
                    }
                }
                TaskKey.GYM.name -> {
                    icon.setImageResource(R.drawable.ic_gym)
                    if (selectedIcon == taskKey.name){
                        iconIndicator.setImageResource(R.drawable.ic_gym_circle_indicator)
                    }else{
                        iconIndicator.setImageResource(0)
                    }
                }
                TaskKey.OTHERS.name -> {
                    icon.setImageResource(R.drawable.ic_others)
                    if (selectedIcon == taskKey.name){
                        iconIndicator.setImageResource(R.drawable.ic_others_circle_indicator)
                    }else{
                        iconIndicator.setImageResource(0)
                    }
                }
            }

            root.setOnClickListener {
                Constants().selectedIcon(context, taskKey.name)
                onItemCheckedListener?.let { it(taskKey) }
            }
        }
    }

    fun setOnCheckedListener(listener: (TaskKey) -> Unit) {
        onItemCheckedListener = listener
    }
}