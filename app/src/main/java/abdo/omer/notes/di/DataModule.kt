package abdo.omer.notes.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import abdo.omer.notes.data.TasksDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val dataModule = module {

    // FerraFoodDB database instance
    single {
        Room.databaseBuilder(androidApplication(), TasksDB::class.java,
            "ferra_food_data.db")
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

    //get shared preferences
    single {
        getSharedPrefs(get())
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(get()).edit()
    }

    //todo refactor repo for both of them
    single { get<TasksDB>().taskDao() }

}


fun getSharedPrefs(context: Context): SharedPreferences {
    return context.getSharedPreferences("default", Context.MODE_PRIVATE)
}


