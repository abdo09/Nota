package abdo.omer.notes.utlis

import abdo.omer.notes.R
import abdo.omer.notes.data.models.Task
import abdo.omer.notes.data.models.TaskKey
import abdo.omer.notes.utlis.Constants
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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

@BindingAdapter("handleTaskIcon")
fun ImageView.handleTaskIcon(photoUrl: String){
    when(photoUrl){
        TaskKey.SHOPPING.name -> {
            this.setImageResource(R.drawable.ic_shopping)
        }
        TaskKey.SPORTS.name -> {
            this.setImageResource(R.drawable.ic_sports)
        }
        TaskKey.GOTO.name -> {
            this.setImageResource(R.drawable.ic_go_to)
        }
        TaskKey.EVENT.name -> {
            this.setImageResource(R.drawable.ic_event)
        }
        TaskKey.GYM.name -> {
            this.setImageResource(R.drawable.ic_gym)
        }
        TaskKey.OTHERS.name -> {
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

@BindingAdapter("handledTaskDoneIndicator")
fun AppCompatImageView.handledTaskDoneIndicator(task: Task){
    when(task.key){
        TaskKey.SHOPPING -> {
            this.setImageResource(R.drawable.shopping_done_indicator)
        }
        TaskKey.SPORTS -> {
            this.setImageResource(R.drawable.sports_done_indicator)
        }
        TaskKey.GOTO -> {
            this.setImageResource(R.drawable.go_to_done_indicator)
        }
        TaskKey.EVENT -> {
            this.setImageResource(R.drawable.event_done_indicator)
        }
        TaskKey.GYM -> {
            this.setImageResource(R.drawable.gym_done_indicator)
        }
        TaskKey.OTHERS -> {
            this.setImageResource(R.drawable.others_done_indicator)
        }
    }
}

@BindingAdapter("handleTaskIconIndicator")
fun ImageView.handleTaskIconIndicator(photoUrl: String){
    val selectedIcon = Constants().selectedIcon(context = this.context)
    when(photoUrl){
        TaskKey.SHOPPING.name -> {
            if (selectedIcon == photoUrl){
                this.setImageResource(R.drawable.ic_shopping_circle_indicator)
            }else{
                this.setImageResource(0)
            }
        }
        TaskKey.SPORTS.name -> {
            if (selectedIcon == photoUrl){
                this.setImageResource(R.drawable.ic_sports_circle_indicator)
            }else{
                this.setImageResource(0)
            }
        }
        TaskKey.GOTO.name -> {
            if (selectedIcon == photoUrl){
                this.setImageResource(R.drawable.ic_go_to_circle_indicator)
            }else{
                this.setImageResource(0)
            }
        }
        TaskKey.EVENT.name -> {
            if (selectedIcon == photoUrl){
                this.setImageResource(R.drawable.ic_event_circle_indicator)
            }else{
                this.setImageResource(0)
            }
        }
        TaskKey.GYM.name -> {
            if (selectedIcon == photoUrl){
                this.setImageResource(R.drawable.ic_gym_circle_indicator)
            }else{
                this.setImageResource(0)
            }
        }
        TaskKey.OTHERS.name -> {
            if (selectedIcon == photoUrl){
                this.setImageResource(R.drawable.ic_others_circle_indicator)
            }else{
                this.setImageResource(0)
            }
        }
    }
}

@BindingAdapter("handledTaskTime")
fun TextView.handledTaskTime(task: Task){
    val times = handleTime(task = task)
    this.text = times
}

@BindingAdapter("handleCheckBox")
fun ImageView.handleCheckBox(task: Task){
    if (!task.taskIsDone) {
        this.setImageResource(0)
    } else {
        this.setImageResource(R.drawable.ic_mark_done)
    }
}