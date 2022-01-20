package abdo.omer.notes.ui.completedTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import abdo.omer.notes.base.BaseSupportFragment
import abdo.omer.notes.databinding.FragmentCompletedTasksBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class CompletedTasksFragment : BaseSupportFragment() {

    override val viewModel by viewModel<CompletedTaskViewModel>()

    private lateinit var binding: FragmentCompletedTasksBinding
    private lateinit var completedTaskAdapter: CompletedTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompletedTasksBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        getTasksFromDB()

        viewmodelObserver()
    }

    private fun setupRecyclerView() {
        completedTaskAdapter = CompletedTaskAdapter()
        binding.completedTasksRecyclerView.apply {
            adapter = completedTaskAdapter
        }
    }

    private fun getTasksFromDB() {
        viewModel.launch(Dispatchers.IO) {
            viewModel.getAllTasks()
        }
    }

    private fun viewmodelObserver() {
        viewModel.taskList.observe(viewLifecycleOwner, {
            completedTaskAdapter.differ.submitList(it)
        })
    }

}