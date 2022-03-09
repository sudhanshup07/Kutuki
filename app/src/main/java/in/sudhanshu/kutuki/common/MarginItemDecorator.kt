package `in`.sudhanshu.kutuki.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0
                || parent.getChildAdapterPosition(view) == 1) {

                left =  spaceHeight*3
            }
            top = spaceHeight/2
            right = spaceHeight*2
            bottom = spaceHeight/2
        }
    }
}