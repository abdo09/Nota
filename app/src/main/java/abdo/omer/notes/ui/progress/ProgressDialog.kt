package abdo.omer.notes.ui.progress

import abdo.omer.notes.R
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle

class ProgressDialog(context: Context?) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //setCancelable(true)
        setContentView(R.layout.prgress_dialog_layout)
    }


    init {
        setCanceledOnTouchOutside(false)
    }
}