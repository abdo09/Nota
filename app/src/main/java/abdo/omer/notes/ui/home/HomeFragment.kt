package abdo.omer.notes.ui.home

import abdo.omer.notes.base.BaseSupportFragment
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.databinding.FragmentHomeBinding
import abdo.omer.notes.ui.home.adapters.TasksAdapter
import abdo.omer.notes.ui.home.adapters.TasksDoneAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.ArrayList


class HomeFragment : BaseSupportFragment() {

    override val viewModel by viewModel<HomeFragmentViewModel>()

    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var tasksDoneAdapter: TasksDoneAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        onClick()

        viewModel.launch(Dispatchers.IO) {
            getTasksFromDB()
        }

        viewModel.taskList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                val notFinishedTasks = it.filter { task -> !task.taskIsDone }
                tasksAdapter.differ.submitList(notFinishedTasks)
                tasksDoneAdapter.differ.submitList(notFinishedTasks)
            }
        }
    }


    private fun getTasksFromDB() {
        viewModel.getAllTasks()
    }

    private fun setupRecyclerView() {
        tasksAdapter = TasksAdapter()
        tasksDoneAdapter = TasksDoneAdapter()
        binding.tasksRecyclerView.apply {
            adapter = tasksAdapter
        }
        binding.tasksRecyclerViewDone.apply {
            adapter = tasksDoneAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onClick() {
        binding.btnFinishTasks.setOnClickListener {
            viewModel.isDone = !viewModel.isDone
            if (viewModel.isDone) {
                binding.tasksRecyclerView.visibility = View.GONE
                binding.tasksRecyclerViewDone.visibility = View.VISIBLE
                binding.doAndUndoChangesLayout.visibility = View.VISIBLE
                binding.calendarAddTaskLayout.visibility = View.GONE
            }
        }

        binding.btnOpenCalendar.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionNavHomeFragmentToNavCalenderFragment())
        }

        binding.btnCompletedTasks.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionNavHomeFragmentToNavCompletedTasksFragment())
        }

        binding.btnDoChanges.setOnClickListener {
            val notFinishedTasks = tasksDoneAdapter.differ.currentList.filter { task -> !task.taskIsDone }
            val finishedTasks = tasksDoneAdapter.differ.currentList.filter { task -> task.taskIsDone }
            tasksDoneAdapter.differ.submitList(notFinishedTasks)
            tasksAdapter.differ.submitList(notFinishedTasks)
            tasksDoneAdapter.notifyDataSetChanged()
            viewModel.launch(Dispatchers.IO) {
                finishedTasks.forEach {
                    viewModel.upsertTask(it)
                }
            }
            handleButtonsVisibility()
        }

        binding.btnUndoChanges.setOnClickListener {
            tasksDoneAdapter.differ.currentList.forEach {
                if (it.taskIsDone) it.taskIsDone = false
            }
            tasksDoneAdapter.differ.submitList(tasksDoneAdapter.differ.currentList)
            tasksAdapter.differ.submitList(tasksDoneAdapter.differ.currentList)
            tasksDoneAdapter.notifyDataSetChanged()
            handleButtonsVisibility()
        }

        binding.btnNavAddTask.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionNavHomeFragmentToAddTaskFragment())
        }

        binding.btnAddTask.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionNavHomeFragmentToAddTaskFragment())
        }

        tasksAdapter.setOnItemClickListener { task ->
            navController.navigate(HomeFragmentDirections.actionNavSentBottomSheetFragment(task = task))
        }

    }

    private fun handleButtonsVisibility() {
        viewModel.isDone = !viewModel.isDone
        if (!viewModel.isDone) {
            binding.tasksRecyclerView.visibility = View.VISIBLE
            binding.tasksRecyclerViewDone.visibility = View.GONE
            binding.calendarAddTaskLayout.visibility = View.VISIBLE
            binding.doAndUndoChangesLayout.visibility = View.GONE
        }
    }
}