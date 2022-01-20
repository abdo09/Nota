package abdo.omer.notes.ui

import abdo.omer.notes.R
import abdo.omer.notes.utlis.navigationBarAndStatusBarColor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.navigationBarAndStatusBarColor(R.color.status_bar_color, R.color.navigation_bar_color)
    }
}