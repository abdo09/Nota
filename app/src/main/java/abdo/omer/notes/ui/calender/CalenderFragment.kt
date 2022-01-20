package abdo.omer.notes.ui.calender

import abdo.omer.notes.base.BaseSupportFragment
import abdo.omer.notes.databinding.FragmentCalenderBinding
import abdo.omer.notes.ui.calender.adapter.CalendarDateAdapter
import abdo.omer.notes.ui.calender.adapter.CalendarTaskAdapter
import abdo.omer.notes.ui.home.adapters.TasksAdapter
import abdo.omer.notes.ui.home.adapters.TasksDoneAdapter
import abdo.omer.notes.utlis.getAllDateOfMonth
import abdo.omer.notes.utlis.getMonth
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.util.*

class CalenderFragment : BaseSupportFragment() {

    override val viewModel by viewModel<CalendarViewModel>()

    private lateinit var binding: FragmentCalenderBinding
    private lateinit var calendarDateAdapter: CalendarDateAdapter
    private lateinit var calendarTaskAdapter: CalendarTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentCalenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        getCalendarInstance()

        getTasksFromDB()

        viewmodelObserver()

        onClickListener()

    }

    @SuppressLint("SetTextI18n")
    private fun getCalendarInstance() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)

        val month = getMonth(calendar.get(Calendar.MONTH))
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)

        binding.fragmentTitle.text = "$month $year"

        Timber.d("HOURS = $hour:$minute")

        val dateList = getAllDateOfMonth(year, month)
        calendarDateAdapter.differ.submitList(dateList)
    }

    private fun getTasksFromDB() {
        viewModel.launch(Dispatchers.IO) {
            viewModel.getAllTasks()
        }
    }

    private fun viewmodelObserver() {
        viewModel.taskList.observe(viewLifecycleOwner, {
            calendarTaskAdapter.differ.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        calendarDateAdapter = CalendarDateAdapter(requireContext())
        calendarTaskAdapter = CalendarTaskAdapter()
        binding.calendarRecycler.apply {
            adapter = calendarDateAdapter
        }
        binding.calendarTasksRecycler.apply {
            adapter = calendarTaskAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onClickListener() {
        calendarDateAdapter.setOnCheckedListener {
            calendarDateAdapter.notifyDataSetChanged()
        }
    }

}