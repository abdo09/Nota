package abdo.omer.notes.base

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

abstract class BaseSupportActivity: AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

}