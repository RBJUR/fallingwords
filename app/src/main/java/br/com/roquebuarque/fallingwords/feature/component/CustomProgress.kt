package br.com.roquebuarque.fallingwords.feature.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CustomProgress(context: Context, attr: AttributeSet) : View(context, attr) {

    private val correctRectf = RectF()
    private val wrongRectf = RectF()
    private val correctPaint = Paint()
    private val wrongPaint = Paint()


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        with(canvas) {

            if (width != 0) {
                correctPaint.apply {
                    color = Color.GREEN
                    flags = Paint.ANTI_ALIAS_FLAG
                }

                wrongPaint.apply {
                    color = Color.RED
                    flags = Paint.ANTI_ALIAS_FLAG
                }

                canvas.drawRect(correctRectf, correctPaint)
                canvas.drawRect(wrongRectf, wrongPaint)
            }

        }
    }


    fun setup(countCorrect: Int, countWrong: Int, totalQuestion: Int) {
        if (totalQuestion != 0) {

            val totalWidth = (width / totalQuestion)
            val correctWidth = totalWidth * countCorrect
            val wrongWidth = totalWidth * countWrong

            correctRectf.left = correctWidth.toFloat()
            correctRectf.right = 0f
            correctRectf.top = 0f
            correctRectf.bottom = 30f

            wrongRectf.left = correctWidth.toFloat() + wrongWidth.toFloat()
            wrongRectf.right = correctWidth.toFloat()
            wrongRectf.top = 0f
            wrongRectf.bottom = 30f

            invalidate()
        }
    }
}