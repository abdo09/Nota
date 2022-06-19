package abdo.omer.notes.ui.calender

import abdo.omer.notes.base.BaseSupportFragment
import abdo.omer.notes.data.models.DateModel
import abdo.omer.notes.databinding.FragmentCalenderBinding
import abdo.omer.notes.ui.calender.adapter.CalendarDateAdapter
import abdo.omer.notes.ui.calender.adapter.CalendarTaskAdapter
import abdo.omer.notes.ui.calender.adapter.CenterLayoutManager
import abdo.omer.notes.ui.home.HomeFragmentDirections
import abdo.omer.notes.utlis.*
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.time.LocalDate
import java.time.Month
import java.util.*

class CalenderFragment : BaseSupportFragment() {

    override val viewModel by viewModel<CalendarViewModel>()

    private var binding: FragmentCalenderBinding? = null
    private lateinit var calendarDateAdapter: CalendarDateAdapter
    private lateinit var calendarTaskAdapter: CalendarTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalenderBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        getCalendarInstance()

        getTasksFromDB()

        viewmodelObserver()

        onClickListener()

        val s = Constants().selectedDatePosition(requireContext())
        viewModel.goToPosition.postValue(s)
    }

    override fun setOnclickLister() {
        binding?.btnBack?.setOnClickListener {
            navController.navigate(CalenderFragmentDirections.actionNavCalenderFragmentToNavHomeFragment())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getCalendarInstance() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = getMonth(calendar.get(Calendar.MONTH))
        val dateModel = Constants().getDate(requireContext())

        if (dateModel != null) {
            binding?.fragmentTitle?.text = "${dateModel.month} ${dateModel.year}"
            val dateList = getAllDateOfMonth(dateModel.year, dateModel.month)
            calendarDateAdapter.differ.submitList(dateList)
        } else {
            binding?.fragmentTitle?.text = "$month $year"
            val dateList = getAllDateOfMonth(year, month)
            calendarDateAdapter.differ.submitList(dateList)
        }
    }

    private fun getTasksFromDB() {
        viewModel.launch(Dispatchers.IO) {
            viewModel.getAllTasks()
        }
    }

    private fun viewmodelObserver() {
        viewModel.taskList.observe(viewLifecycleOwner) {
            val date = Constants().getDate(requireContext())
            if (date == null) {
                val notFinishedTasks = it.filter { task -> !task.taskIsDone }
                calendarTaskAdapter.differ.submitList(notFinishedTasks)
            } else {
                sortTaskListOfTheDate(date = date.dayOfMonth, month = date.month, year = date.year)
            }
        }

        viewModel.goToPosition.observe(viewLifecycleOwner) {
            if (it != -1) requireContext().postToLooper(delay = 500){
                binding?.calendarRecycler?.smoothScrollToPosition(it)
            }
        }
    }

    private fun setupRecyclerView() {
        calendarDateAdapter = CalendarDateAdapter(requireContext())
        calendarTaskAdapter = CalendarTaskAdapter()
        binding?.calendarRecycler?.apply {
            adapter = calendarDateAdapter
            layoutManager =
                CenterLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        binding?.calendarTasksRecycler?.apply {
            adapter = calendarTaskAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onClickListener() {

        calendarTaskAdapter.setOnTaskClickListener { task ->
            navController.navigate(HomeFragmentDirections.actionNavSentBottomSheetFragment(task = task))
        }

        calendarDateAdapter.setOnCheckedListener { date ->
            sortTaskListOfTheDate(date = date.dayOfMonth, month = date.month, year = date.year)
        }
        binding?.fragmentTitle?.setOnClickListener {
            handleDatePicker()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortTaskListOfTheDate(date: Int, month: Month?, year: Int) {
        val listOfTheDay =
            viewModel.taskList.value
                ?.asSequence()
                ?.filter { !it.taskIsDone }
                ?.filter { task ->
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendar.time = Date(task.day.dayInLong)
                    calendar.get(Calendar.YEAR) == year
                }
                ?.filter { task ->
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendar.time = Date(task.day.dayInLong)
                    getMonth(calendar.get(Calendar.MONTH)) == month
                }
                ?.filter { it.day.dayOfTheMonth == date.toString() }
                ?.sortedBy { it.time.hourAndMinute?.replaceDoubleQuote()?.toInt() }
                ?.toList()

        calendarTaskAdapter.differ.submitList(listOfTheDay)
        calendarTaskAdapter.notifyDataSetChanged()
        calendarDateAdapter.apply {
            notifyDataSetChanged()
        }
        requireContext().postToLooper(delay = 500){
            binding?.calendarRecycler?.smoothScrollToPosition(date - 1)
        }
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun handleDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.show(childFragmentManager, "DATE PICKER")
        datePicker.addOnPositiveButtonClickListener {

            // Create calendar object and set the date to be that returned from selection
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(it)
            val year = calendar.get(Calendar.YEAR)
            val month = getMonth(calendar.get(Calendar.MONTH))
            viewModel.day = calendar.get(Calendar.DAY_OF_MONTH)
            Constants().selectedDatePosition(requireContext(), viewModel.day - 1)
            val date = DateModel(
                dayOfMonth = viewModel.day,
                month = month,
                year = year
            )
            Constants().setDate(requireContext(), dateModel = date)

            binding?.fragmentTitle?.text = "$month $year"

            val dateList = getAllDateOfMonth(year, month)
            calendarDateAdapter.differ.submitList(dateList)
            calendarDateAdapter.notifyDataSetChanged()

            sortTaskListOfTheDate(viewModel.day, month, year)

            requireContext().postToLooper(delay = 250){
                binding?.calendarRecycler?.smoothScrollToPosition(viewModel.day - 1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.calendarRecycler?.adapter = null
        binding?.calendarTasksRecycler?.adapter = null
        binding = null
    }

}