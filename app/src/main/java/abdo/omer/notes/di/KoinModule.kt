package abdo.omer.notes.di

import abdo.omer.notes.data.repository.roomRepository.TaskRoomRepository
import abdo.omer.notes.data.repository.roomRepository.TaskRoomRepositoryImp
import abdo.omer.notes.ui.addTask.AddTaskViewModel
import abdo.omer.notes.ui.calender.CalendarViewModel
import abdo.omer.notes.ui.completedTask.CompletedTaskViewModel
import abdo.omer.notes.ui.home.HomeFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    viewModel { HomeFragmentViewModel(get()) }
    viewModel { CalendarViewModel(get()) }
    viewModel { CompletedTaskViewModel(get()) }
    viewModel { AddTaskViewModel(get()) }

    factory<TaskRoomRepository> { TaskRoomRepositoryImp(get()) }

}