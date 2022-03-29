package abdo.omer.notes.ui.addTask

import abdo.omer.notes.R
import abdo.omer.notes.base.BaseSupportFragment
import abdo.omer.notes.data.models.TaskKey
import abdo.omer.notes.databinding.FragmentAddTaskBinding
import abdo.omer.notes.utlis.*
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class AddTaskFragment : BaseSupportFragment() {

    override val viewModel by viewModel<AddTaskViewModel>()

    private var binding: FragmentAddTaskBinding? = null
    private lateinit var addTaskIconAdapter: AddTaskIconAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        onclickListener()
        setBoarderColor()
        onTextChangedListener()
    }

    private fun onTextChangedListener() {
        binding?.edDescription?.setBackgroundColour(context = requireContext())
        binding?.edName?.setColorBoarder(binding?.ipNameLayout)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onclickListener() {
        addTaskIconAdapter.setOnCheckedListener {
            addTaskIconAdapter.notifyDataSetChanged()
            setBoarderColor()
        }

        binding?.btnOpenDate?.setOnClickListener {
            handleDatePicker()
        }

        binding?.btnOpenTime?.setOnClickListener {
            handleTimePicker()
        }

        binding?.btnAddTask?.setOnClickListener {
            if (fieldsValidate()) {
                viewModel.upsertTask(viewModel.task)
                navController.navigate(AddTaskFragmentDirections.actionAddTaskFragmentToNavHomeFragment())
            } else CookieBarConfig(
                requireActivity()
            ).showDefaultErrorCookie("Check all the fields")
        }

        binding?.btnNavHomeFragment?.setOnClickListener {
            navController.navigate(AddTaskFragmentDirections.actionAddTaskFragmentToNavHomeFragment())
        }
    }

    private fun fieldsValidate(): Boolean {
        val name = binding?.edName?.text.toString()
        val description = binding?.edDescription?.text.toString()
        val date = viewModel.task.day.dayInLong
        val time = viewModel.task.time

        Timber.d("TIMEOFTASK = ${time.hours}\n${time.minute}")

        if (name.isEmpty()) binding?.ipNameLayout?.setBoarder(R.color.text_input_stroke_red_color)
        else viewModel.task.name = name

        if (description.isEmpty()) binding?.edDescription?.background =
            descriptionDrawable(R.color.red_400, requireContext())
        else viewModel.task.description = description

        return (name.isNotEmpty() && description.isNotEmpty() && date != 0L && time.hours != null && time.minute != null)
    }

    @SuppressLint("SetTextI18n")
    private fun handleTimePicker() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select time")
            .build()

        timePicker.show(childFragmentManager, "TIME PICKER")

        timePicker.addOnPositiveButtonClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute

            timePicker.inputMode
            var time = ""

            if (hour <= 9) {
                time = "0$hour:$minute"
                binding?.tvTime?.text = time
                viewModel.task.time.hours = hour
                viewModel.task.time.minute = minute
            }
            if (minute <= 9) {
                time = "$hour:0$minute"
                binding?.tvTime?.text = time
                viewModel.task.time.hours = hour
                viewModel.task.time.minute = minute
            }
            if (hour <= 9 && minute <= 9) {
                time = "0$hour:0$minute"
                binding?.tvTime?.text = time
                viewModel.task.time.hours = hour
                viewModel.task.time.minute = minute
            }
            if (hour > 9 && minute > 9) {
                time = "$hour:$minute"
                binding?.tvTime?.text = time
                viewModel.task.time.hours = hour
                viewModel.task.time.minute = minute
            }
            viewModel.task.time.hourAndMinute = time
        }
    }

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
            val date = "${calendar.get(Calendar.DAY_OF_MONTH)}-" +
                    "${getMonth(calendar.get(Calendar.MONTH))}-${calendar.get(Calendar.YEAR)}"
            binding?.tvDate?.text = date

            viewModel.task.day.dayInLong = it
            viewModel.task.day.dayOfTheWeek =
                getMonth(calendar.get(Calendar.MONTH)).name.substring(0, 3)
            viewModel.task.day.dayOfTheMonth = calendar.get(Calendar.DAY_OF_MONTH).toString()
            viewModel.task.day.month = getMonth(calendar.get(Calendar.MONTH)).toString()
            viewModel.task.day.year = calendar.get(Calendar.YEAR).toString()
        }
    }

    private fun setupRecyclerView() {
        addTaskIconAdapter = AddTaskIconAdapter(requireContext())
        addTaskIconAdapter.setHasStableIds(true)
        binding?.iconsRecyclerView?.apply {
            adapter = addTaskIconAdapter
            this.hasFixedSize()
        }
        if (Constants().selectedIcon(requireContext())?.isEmpty() == true)
            Constants().selectedIcon(requireContext(), TaskKey.SHOPPING.name)
        addTaskIconAdapter.differ.submitList(TaskKey.values().toList())
    }

    private fun setBoarderColor() {
        val selectedIcon = Constants().selectedIcon(requireContext())
        if (selectedIcon == TaskKey.SHOPPING.name) {
            boarderColorRes(
                ipNameLayout = binding?.ipNameLayout,
                strokeColor = R.color.text_input_stroke_shopping_color,
                view = binding?.view,
                backgroundColor = R.color.shopping_color,
                view2 = binding?.view2,
                editText = binding?.edDescription,
                taskKey = TaskKey.SHOPPING
            )
        }
        if (selectedIcon == TaskKey.SPORTS.name) {
            boarderColorRes(
                ipNameLayout = binding?.ipNameLayout,
                strokeColor = R.color.text_input_stroke_sports_color,
                view = binding?.view,
                backgroundColor = R.color.sports_color,
                view2 = binding?.view2,
                editText = binding?.edDescription,
                taskKey = TaskKey.SPORTS
            )
        }
        if (selectedIcon == TaskKey.GOTO.name) {
            boarderColorRes(
                ipNameLayout = binding?.ipNameLayout,
                strokeColor = R.color.text_input_stroke_go_to_color,
                view = binding?.view,
                backgroundColor = R.color.go_to_color,
                view2 = binding?.view2,
                editText = binding?.edDescription,
                taskKey = TaskKey.GOTO
            )

        }
        if (selectedIcon == TaskKey.EVENT.name) {
            boarderColorRes(
                ipNameLayout = binding?.ipNameLayout,
                strokeColor = R.color.text_input_stroke_event_color,
                view = binding?.view,
                backgroundColor = R.color.event_color,
                view2 = binding?.view2,
                editText = binding?.edDescription,
                taskKey = TaskKey.EVENT
            )
        }
        if (selectedIcon == TaskKey.GYM.name) {
            boarderColorRes(
                ipNameLayout = binding?.ipNameLayout,
                strokeColor = R.color.text_input_stroke_gym_color,
                view = binding?.view,
                backgroundColor = R.color.gym_color,
                view2 = binding?.view2,
                editText = binding?.edDescription,
                taskKey = TaskKey.GYM
            )
        }
        if (selectedIcon == TaskKey.OTHERS.name) {
            boarderColorRes(
                ipNameLayout = binding?.ipNameLayout,
                strokeColor = R.color.text_input_stroke_others_color,
                view = binding?.view,
                backgroundColor = R.color.others_color,
                view2 = binding?.view2,
                editText = binding?.edDescription,
                taskKey = TaskKey.OTHERS
            )
        }
    }

    private fun boarderColorRes(
        ipNameLayout: TextInputLayout?,
        strokeColor: Int,
        view: View?,
        backgroundColor: Int,
        view2: View?,
        editText: EditText?,
        taskKey: TaskKey
    ) {
        ipNameLayout?.setBoarder(strokeColor = strokeColor)
        view?.background =
            drawable(context = requireContext(), backgroundColor = backgroundColor)
        view2?.background =
            drawable(context = requireContext(), backgroundColor = backgroundColor)
        editText?.background =
            descriptionDrawable(backgroundColor, requireContext())
        viewModel.task.key = taskKey
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.iconsRecyclerView?.adapter = null
        binding = null
    }

}