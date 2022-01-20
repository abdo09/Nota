package abdo.omer.notes.ui.calender.adapter

import abdo.omer.notes.R
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.databinding.CalendarItemBinding
import abdo.omer.notes.databinding.TasksItemBinding
import abdo.omer.notes.ui.home.adapters.TasksAdapter
import abdo.omer.notes.utlis.Constants
import abdo.omer.notes.utlis.Constants.Companion.SELECTED_DATE
import abdo.omer.notes.utlis.getCustomColor
import abdo.omer.notes.utlis.getCustomDrawable
import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import java.time.LocalDate

class CalendarDateAdapter(val _context: Context) : RecyclerView.Adapter<CalendarDateAdapter.CalendarDateViewHolder>() {

    inner class CalendarDateViewHolder(val calendarItemBinding: CalendarItemBinding) :
        RecyclerView.ViewHolder(calendarItemBinding.root) {
        fun bind(localDate: LocalDate) {
            calendarItemBinding.localDate = localDate
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<LocalDate>() {
        override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDateViewHolder {
        return CalendarDateViewHolder(
            CalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemCheckedListener: ((LocalDate) -> Unit)? = null

    override fun onBindViewHolder(holder: CalendarDateViewHolder, position: Int) {
        val localDate = differ.currentList[position]
        holder.bind(localDate)



        holder.calendarItemBinding.apply {
            dayOfTheMonth.text = localDate.dayOfMonth.toString()
            dayOfTheWeek.text = localDate.dayOfWeek.name.substring(0,3)

            root.setOnClickListener {
                Constants().selectedDate(_context, localDate.dayOfMonth)
                onItemCheckedListener?.let { it(localDate) }
            }

            val selectedDate = Constants().selectedDate(_context)
            if (selectedDate == localDate.dayOfMonth){
                root.background = _context.getCustomDrawable(R.drawable.gradient_blue)
                holder.calendarItemBinding.dayOfTheMonth.setTextColor(_context.getCustomColor(R.color.white))
                holder.calendarItemBinding.dayOfTheWeek.setTextColor(_context.getCustomColor(R.color.white))
            } else{
                root.background = _context.getCustomDrawable(R.color.white)
                holder.calendarItemBinding.dayOfTheMonth.setTextColor(_context.getCustomColor(R.color.black))
                holder.calendarItemBinding.dayOfTheWeek.setTextColor(_context.getCustomColor(R.color.black))
            }
        }
    }

    fun setOnCheckedListener(listener: (LocalDate) -> Unit) {
        onItemCheckedListener = listener
    }
}