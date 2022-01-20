package abdo.omer.notes.utlis

import abdo.omer.notes.R
import abdo.omer.notes.data.models.TaskKey
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

fun Context.loadWithGlide(
    into: ImageView?,
    url: Any?,
    fitImage: Boolean = false,
    roundImage: Boolean = false,
    listener: RequestListener<Drawable>? = null
) {
    if (url == null)
        return
    val circularProgressDrawable = CircularProgressDrawable(this)
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 100f
    circularProgressDrawable.setStyle(CircularProgressDrawable.DEFAULT)
    circularProgressDrawable.start()

    val options = RequestOptions()
//            .optionalFitCenter()
        .placeholder(circularProgressDrawable.apply {
            this.backgroundColor = android.R.color.black

        })
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .error(circularProgressDrawable.apply {
            this.backgroundColor = android.R.color.holo_red_dark
        })
        .priority(Priority.HIGH)


    if (fitImage)
        options.fitCenter()
    if (roundImage)
        options.transform(RoundedCorners(16))



    try {
        var imgURI = "" //RemoteConfigs.BASE_URL

        val glide = Glide.with(this).asDrawable()

        if (url is String) {
            when {
                url.startsWith("/data") -> imgURI = url
                url.startsWith("/") -> imgURI = url.replaceFirst("/", "https://")
                url.startsWith("http:") -> imgURI = url.replace("http", "https")
                url.startsWith("https") -> imgURI = url
                else -> imgURI += url
            }

            if (into != null) {
                glide.load(imgURI).apply(options)
                    .listener(listener)
                    .into(into)
            }
        } else {
            if (into != null) {
                glide.load(url).apply(options)
                    .listener(listener)
                    .into(into)
            }

        }

    } catch (ex: Exception) {
        ex.printStackTrace()
    }

}

fun Activity.navigationBarAndStatusBarColor(
    @ColorRes statusColor: Int,
    @ColorRes navigationColor: Int
) {
    val window: Window = this.window
    val decorView = window.decorView

    val wic = WindowInsetsControllerCompat(window, decorView)
    wic.isAppearanceLightStatusBars = true // true or false as desired.
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, statusColor)
    window.navigationBarColor = ContextCompat.getColor(this, navigationColor)
}

fun Context.postToLooper(delay: Long = 0, r: Runnable) {
    val handlerUI = Handler(Looper.getMainLooper())
    handlerUI.postDelayed(r, delay)
}

fun Context.getCustomColor(colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.getCustomDrawable(drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableRes)
}

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun drawable(
    context: Context,
    backgroundColor: Any,
    topLeftCorner: Int = 0,
    topRightCorner: Int = 0,
    bottomLeftCorner: Int = 0,
    bottomRightCorner: Int = 0,
    withStroke: Boolean = true,
    strokeWidth: Int = 2,
    strokeColor: Any = "#D0D0D9",
    withBackground: Boolean = true,
    ovalShape: Boolean = false
): GradientDrawable {

    val drawables = GradientDrawable()
    val topLeft = topLeftCorner.dpToPx.toFloat()
    val topRight = topRightCorner.dpToPx.toFloat()
    val bottomLeft = bottomLeftCorner.dpToPx.toFloat()
    val bottomRight = bottomRightCorner.dpToPx.toFloat()

    if (withBackground) {
        if (backgroundColor is String) {
            drawables.setColor(
                Color.parseColor(
                    backgroundColor
                )
            )
        } else if (backgroundColor is Int) drawables.setColor(
            context.getCustomColor(backgroundColor)
        )

    }

    if (withStroke) {
        if (strokeColor is String) drawables.setStroke(strokeWidth, Color.parseColor(strokeColor))
        else if (strokeColor is Int) drawables.setStroke(
            strokeWidth,
            context.getCustomColor(strokeColor)
        )
    }
    if (ovalShape) drawables.shape = GradientDrawable.OVAL
    if (!ovalShape) drawables.cornerRadii = floatArrayOf(
        // top left
        topLeft,
        topLeft,
        // top right
        topRight,
        topRight,
        // bottom right
        bottomRight,
        bottomRight,
        // bottom left
        bottomLeft,
        bottomLeft
    )
    return drawables
}

fun getMonth(month: Int): Month {
    var m: Month = Month.DECEMBER
    when (month) {
        0 -> m = Month.JANUARY
        1 -> m = Month.FEBRUARY
        2 -> m = Month.MARCH
        3 -> m = Month.APRIL
        4 -> m = Month.MAY
        5 -> m = Month.JUNE
        6 -> m = Month.JULY
        7 -> m = Month.AUGUST
        8 -> m = Month.SEPTEMBER
        9 -> m = Month.OCTOBER
        10 -> m = Month.NOVEMBER
        11 -> m = Month.DECEMBER
    }
    return m
}

fun getAllDateOfMonth(year: Int, month: Month): List<LocalDate> {
    val yearMonth = YearMonth.of(year, month)
    val firstDayOfTheMonth = yearMonth.atDay(1)
    val datesOfThisMonth = mutableListOf<LocalDate>()
    for (daysNo in 0 until yearMonth.lengthOfMonth()) {
        datesOfThisMonth.add(firstDayOfTheMonth.plusDays(daysNo.toLong()))
    }
    return datesOfThisMonth
}

fun TextInputLayout.setBoarder(strokeColor: Int) {
    this.boxStrokeColor = ResourcesCompat.getColor(resources, strokeColor, null)
    this.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(this.context, strokeColor))
}

fun EditText.setColorBoarder(textInputLayout: TextInputLayout) {
    this.addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s?.isEmpty() == true) {
                textInputLayout.setBoarder(R.color.text_input_stroke_red_color)
            } else {
                when (Constants().selectedIcon(context)) {
                    TaskKey.SHOPPING.name -> textInputLayout.setBoarder(R.color.text_input_stroke_shopping_color)
                    TaskKey.SPORTS.name -> textInputLayout.setBoarder(R.color.text_input_stroke_sports_color)
                    TaskKey.GOTO.name -> textInputLayout.setBoarder(R.color.text_input_stroke_go_to_color)
                    TaskKey.EVENT.name -> textInputLayout.setBoarder(R.color.text_input_stroke_event_color)
                    TaskKey.GYM.name -> textInputLayout.setBoarder(R.color.text_input_stroke_gym_color)
                    TaskKey.OTHERS.name -> textInputLayout.setBoarder(R.color.text_input_stroke_others_color)
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
            if (s?.isEmpty() == true) textInputLayout.setBoarder(R.color.text_input_stroke_red_color)
        }

    })
}

fun EditText.setBackgroundColour(context: Context) {
    this.addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s?.isEmpty() == true) {
                this@setBackgroundColour.background =
                descriptionDrawable(context = context, strokeColor = R.color.red_400)
            } else {
                when (Constants().selectedIcon(context)) {
                    TaskKey.SHOPPING.name -> this@setBackgroundColour.background =
                        descriptionDrawable(context = context, strokeColor = R.color.shopping_color)
                    TaskKey.SPORTS.name -> this@setBackgroundColour.background =
                        descriptionDrawable(context = context, strokeColor = R.color.sports_color)
                    TaskKey.GOTO.name -> this@setBackgroundColour.background =
                        descriptionDrawable(context = context, strokeColor = R.color.go_to_color)
                    TaskKey.EVENT.name -> this@setBackgroundColour.background =
                        descriptionDrawable(context = context, strokeColor = R.color.event_color)
                    TaskKey.GYM.name -> this@setBackgroundColour.background =
                        descriptionDrawable(context = context, strokeColor = R.color.gym_color)
                    TaskKey.OTHERS.name -> this@setBackgroundColour.background =
                        descriptionDrawable(context = context, strokeColor = R.color.others_color)
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
            if (s?.isEmpty() == true) descriptionDrawable(context = context, strokeColor = R.color.red_400)
        }

    })
}

fun descriptionDrawable(strokeColor: Int, context: Context): GradientDrawable {
    return drawable(
        context,
        topRightCorner = 10,
        topLeftCorner = 10,
        bottomRightCorner = 10,
        bottomLeftCorner = 10,
        withBackground = true,
        backgroundColor = "#FFFFFF",
        withStroke = true,
        strokeColor = strokeColor,
        strokeWidth = 4
    )
}