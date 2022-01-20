package abdo.omer.notes.utlis

import abdo.omer.notes.R
import android.app.Activity
import com.andrognito.flashbar.Flashbar
import com.andrognito.flashbar.anim.FlashAnim

class CookieBarConfig(val activity: Activity) {
    private val enterAnimation = FlashAnim.with(activity)
            .animateBar()
            .duration(750)
            .alpha()
            .accelerateDecelerate()
    private val exitAnimation = FlashAnim.with(activity)
            .animateBar()
            .duration(400)
            .accelerate()

    private val topAlert =
            Flashbar.Builder(activity)
                    .enableSwipeToDismiss()
                    .messageColorRes(R.color.white)
                    .titleColorRes(R.color.white)
                    .titleSizeInSp(16f)
                    .messageSizeInSp(14f)
                    .enterAnimation(enterAnimation)
                    .exitAnimation(exitAnimation)
                    .castShadow(shadow = true)
                    .gravity(Flashbar.Gravity.TOP)
                    .duration(3000)


    fun showDefaultErrorCookie(error: String) {
        activity.postToLooper {
            topAlert
                    //.title(R.string.error)
                    .message(error)
                    .backgroundDrawable(R.drawable.gradient_error)
                    .showIcon(.8f)
                    .icon(R.drawable.error_red_icon)
                    .vibrateOn(Flashbar.Vibration.SHOW)
                    .show()
        }

    }

    fun showDefaultInfoCookie(message: String) {
        activity.postToLooper {
            topAlert
                    // .title(R.string.info)
                    .message(message)
                    .showIcon(.8f)
                    .backgroundDrawable(R.drawable.gradient_info)
                    .icon(R.drawable.ic_error_white)
                    .show()
        }
    }

    fun showDefaultSuccessCookie(message: String) {
        activity.postToLooper {
            topAlert
                    //.title(R.string.success)
                    .icon(R.drawable.ic_success_white)
                    .backgroundDrawable(R.drawable.gradient_success)
                    .message(message)
                    .show()
        }
    }


}