package abdo.omer.notes.ui.calender.adapter

import abdo.omer.notes.R
import abdo.omer.notes.data.models.DateModel
import abdo.omer.notes.databinding.CalendarItemBinding
import abdo.omer.notes.ui.calender.CalendarViewModel
import abdo.omer.notes.utlis.Constants
import abdo.omer.notes.utlis.getCustomColor
import abdo.omer.notes.utlis.getCustomDrawable
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import java.time.LocalDate
import java.time.Month

class CalendarDateAdapter(private val _context: Context) :
    RecyclerView.Adapter<CalendarDateAdapter.CalendarDateViewHolder>() {

    inner class CalendarDateViewHolder(val calendarItemBinding: CalendarItemBinding) :
        RecyclerView.ViewHolder(calendarItemBinding.root) {
        fun bind(localDate: LocalDate) {
            calendarItemBinding.localDate = localDate
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<LocalDate>() {
        override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
            return oldItem.dayOfMonth == newItem.dayOfMonth
        }

        override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
            return oldItem.dayOfMonth == newItem.dayOfMonth
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
        val localDateItem = differ.currentList[position]
        holder.bind(localDateItem)

        holder.calendarItemBinding.apply {
            val selectedDate = Constants().selectedDatePosition(_context) + 1
            dayOfTheMonth.text = localDateItem.dayOfMonth.toString()
            dayOfTheWeek.text = localDateItem.dayOfWeek.name.substring(0, 3)

            root.setOnClickListener {
                val date = DateModel(
                    year = localDateItem.year,
                    month = localDateItem.month,
                    dayOfMonth = localDateItem.dayOfMonth
                )
                Constants().setDate(_context, date)
                Constants().selectedDatePosition(_context, position)
                onItemCheckedListener?.let { it(localDateItem) }
                handleItemBackgroundColor(holder.calendarItemBinding, selectedDate, localDateItem)
            }

            handleItemBackgroundColor(holder.calendarItemBinding, selectedDate, localDateItem)
        }
    }

    private fun handleItemBackgroundColor(
        calendarItemBinding: CalendarItemBinding,
        selectedDate: Int,
        localDate: LocalDate
    ) {
        calendarItemBinding.apply {
            if (selectedDate == localDate.dayOfMonth) {
                root.background = _context.getCustomDrawable(R.drawable.gradient_blue)
                dayOfTheMonth.setTextColor(_context.getCustomColor(R.color.white))
                dayOfTheWeek.setTextColor(_context.getCustomColor(R.color.white))
            } else {
                root.background = _context.getCustomDrawable(R.color.white)
                dayOfTheMonth.setTextColor(_context.getCustomColor(R.color.black))
                dayOfTheWeek.setTextColor(_context.getCustomColor(R.color.black))
            }
        }
    }

    fun setOnCheckedListener(listener: (LocalDate) -> Unit) {
        onItemCheckedListener = listener
    }
}