package abdo.omer.notes

import abdo.omer.notes.di.appModules
import abdo.omer.notes.di.dataModule
import abdo.omer.notes.di.firebaseModule
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class TasksApp: MultiDexApplication() {

    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        //firebase initialization on the app level
        FirebaseApp.initializeApp(this)

        configureTimber()

        configureKoin()

    }

    private fun configureTimber() {
        //Timber used for logging
        // Logging in Debug build, in release log only crashes
        Timber.plant(Timber.DebugTree())
        /*if (BuildConfig.FLAVOR == "development" || BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(CrashReportingTree())
        Timber.d("BuildConfig.DEBUG ${BuildConfig.DEBUG} ${BuildConfig.BUILD_TYPE} ${BuildConfig.FLAVOR}")*/
    }

    private fun configureKoin() {
        startKoin {
            fragmentFactory()

            // use the Android context given there
            androidContext(this@TasksApp)
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger(Level.ERROR)
            // load properties from assets/koin.properties file
            androidFileProperties()

            modules(listOf(dataModule, firebaseModule, appModules))
        }
    }

}