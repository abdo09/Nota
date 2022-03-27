package abdo.omer.notes.ui.calender.adapter

import abdo.omer.notes.R
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.data.models.TaskKey
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("handledTaskIcon")
fun AppCompatImageView.handledTaskIcon(task: Task){
    when(task.key){
        TaskKey.SHOPPING -> {
            this.setImageResource(R.drawable.ic_shopping)
        }
        TaskKey.SPORTS -> {
            this.setImageResource(R.drawable.ic_sports)
        }
        TaskKey.GOTO -> {
            this.setImageResource(R.drawable.ic_go_to)
        }
        TaskKey.EVENT -> {
            this.setImageResource(R.drawable.ic_event)
        }
        TaskKey.GYM -> {
            this.setImageResource(R.drawable.ic_gym)
        }
        TaskKey.OTHERS -> {
            this.setImageResource(R.drawable.ic_others)
        }
    }
}

@BindingAdapter("handledTaskIndicator")
fun AppCompatImageView.handledTaskIndicator(task: Task){
    when(task.key){
        TaskKey.SHOPPING -> {
            this.setImageResource(R.drawable.ic_shopping_circle_indicator)
        }
        TaskKey.SPORTS -> {
            this.setImageResource(R.drawable.ic_sports_circle_indicator)
        }
        TaskKey.GOTO -> {
            this.setImageResource(R.drawable.ic_go_to_circle_indicator)
        }
        TaskKey.EVENT -> {
            this.setImageResource(R.drawable.ic_event_circle_indicator)
        }
        TaskKey.GYM -> {
            this.setImageResource(R.drawable.ic_gym_circle_indicator)
        }
        TaskKey.OTHERS -> {
            this.setImageResource(R.drawable.ic_others_circle_indicator)
        }
    }
}