package ui.anwesome.com.ashapedladderview

/**
 * Created by anweshmishra on 03/04/18.
 */

import android.app.Activity
import android.view.*
import android.content.*
import android.graphics.*

class AShapedLadderView (ctx : Context) : View (ctx) {

    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val renderer : Renderer = Renderer(this)

    override fun onDraw (canvas : Canvas) {
        renderer.render(canvas, paint)
    }

    override fun onTouchEvent (event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class State (var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {
        fun update (stopcb : (Float) -> Unit) {
            scale += 0.1f * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }

        fun startUpdating (startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class Animator (var view : View, var animated : Boolean = false) {
        fun animate (updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }

        fun start () {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop () {
            if (animated) {
                animated = false
            }
        }
    }

    data class AShapedLadder (var i : Int, val state : State = State()) {

        fun draw (canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            val deg = 30f * state.scale
            paint.color = Color.parseColor("#27ae60")
            canvas.save()
            canvas.translate(w/2, h/3)
            for (i in 0..1) {
                canvas.save()
                canvas.rotate(deg * (1 - 2 * i))
                canvas.drawLine(0f, 0f, 0f, w / 3, paint)
                canvas.restore()
            }
            val gap : Float = w/(3 * 5)
            var x : Float = gap
            for (j in 0..3) {
                val px = x * Math.sin(deg * Math.PI/180).toFloat() * (1 - 2 * i)
                val py = x * Math.cos(deg * Math.PI/180).toFloat()
                canvas.drawLine(-px, py, px, py, paint)
                x += gap
            }
            canvas.restore()
        }

        fun update (stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating (startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer (var view : AShapedLadderView) {

        val animator : Animator = Animator(view)

        val ladder : AShapedLadder = AShapedLadder(0)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            ladder.draw(canvas, paint)
            animator.animate {
                ladder.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            ladder.startUpdating {
                animator.start()
            }
        }
    }

    companion object {
        fun create (activity : Activity) : AShapedLadderView {
            val view = AShapedLadderView(activity)
            activity.setContentView(view)
            return view
        }
    }
}