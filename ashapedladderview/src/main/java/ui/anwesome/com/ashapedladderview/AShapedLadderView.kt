package ui.anwesome.com.ashapedladderview

/**
 * Created by anweshmishra on 03/04/18.
 */

import android.view.*
import android.content.*
import android.graphics.*

class AShapedLadderView (ctx : Context) : View (ctx) {

    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw (canvas : Canvas) {

    }

    override fun onTouchEvent (event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}