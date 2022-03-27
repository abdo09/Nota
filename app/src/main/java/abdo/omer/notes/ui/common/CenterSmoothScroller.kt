package abdo.omer.notes.ui.common

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller

open class CenterSmoothScroller(context: Context?, private val speeroChildCount: Int) : LinearSmoothScroller(context) {

    companion object {
        private const val MILLISECONDS_PER_INCH = 16f

    }

    override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {

        return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2)

    }


    /**
     * Calculates the scroll speed.
     *
     *
     * By default, LinearSmoothScroller assumes this method always returns the same value and
     * caches the result of calling it.
     *
     * @param displayMetrics DisplayMetrics to be used for real dimension calculations
     * @return The time (in ms) it should take for each pixel. For instance, if returned value is
     * 2 ms, it means scrolling 1000 pixels with LinearInterpolation should take 2 seconds.
     */
    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return when {
            speeroChildCount < 10 -> 4 * MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            speeroChildCount < 20 -> 3 * MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            speeroChildCount < 30 -> 2 * MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            else -> MILLISECONDS_PER_INCH / displayMetrics.densityDpi
        }

    }


    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }


}