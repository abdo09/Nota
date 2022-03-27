package abdo.omer.notes.ui.common

import abdo.omer.notes.R
import abdo.omer.notes.data.models.TaskKey
import abdo.omer.notes.databinding.FragmentTaskDetailsBottomSheetBinding
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SubTasksBottomSheetFragment : BottomSheetDialogFragment() {

    private val args by navArgs<TaskDetailsBottomSheetFragmentArgs>()
    private lateinit var binding: FragmentTaskDetailsBottomSheetBinding
    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailsBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareView()

        onClickListener()
    }

    private fun onClickListener() {
        binding.btnDone.setOnClickListener {
            dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun prepareView() {

        setTaskIcon()

        val hour = args.task.time.hours!!
        val minute = args.task.time.minute!!
        var time = ""
        if (hour <= 9) {
            time = "0$hour:$minute"
        }
        if (minute <= 9) {
            time = "$hour:0$minute"
        }
        if (hour <= 9 && minute <= 9) {
            time = "0$hour:0$minute"
        }
        if (hour > 9 && minute > 9) {
            time = "$hour:$minute"
        }

        binding.taskName.text = args.task.name
        binding.taskDate.text = "${args.task.day.dayOfTheMonth} ${args.task.day.dayOfTheWeek}"
        binding.taskTime.text = time
        binding.taskDescription.text = args.task.description
    }

    private fun setTaskIcon() {
        when(args.task.key){
            TaskKey.SHOPPING -> {
                binding.taskIcon.setImageResource(R.drawable.ic_shopping)
            }
            TaskKey.SPORTS -> {
                binding.taskIcon.setImageResource(R.drawable.ic_sports)
            }
            TaskKey.GOTO -> {
                binding.taskIcon.setImageResource(R.drawable.ic_go_to)
            }
            TaskKey.EVENT -> {
                binding.taskIcon.setImageResource(R.drawable.ic_event)
            }
            TaskKey.GYM -> {
                binding.taskIcon.setImageResource(R.drawable.ic_gym)
            }
            TaskKey.OTHERS -> {
                binding.taskIcon.setImageResource(R.drawable.ic_others)
            }
        }
    }

}