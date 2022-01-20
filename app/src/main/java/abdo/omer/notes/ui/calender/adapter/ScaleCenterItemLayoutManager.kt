package abdo.omer.notes.ui.calender.adapter


import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.min

class ScaleCenterItemLayoutManager : LinearLayoutManager {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    ) {
        // some code
    }

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
        lp?.width = width / 3
        return true
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        scaleMiddleItem()
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
        return if (orientation == RecyclerView.HORIZONTAL) {
            scaleMiddleItem()
            scrolled
        } else {
            0
        }
    }

    private fun scaleMiddleItem() {
        val mid = width / 2.0f
        val d1 = 0.9f * mid
        for (i in 0..childCount) {
            val child = getChildAt(i)
            val childMid = child?.let { (getDecoratedRight(it) + getDecoratedLeft(it)) / 2.0f }
            val d = childMid?.let { min(d1, abs(mid - it)) }
            val scale = d?.let { 1f - 0.15f * it / d1 }
            scale?.let {
                child.scaleX = it
                child.scaleY = it
            }
        }
    }
}