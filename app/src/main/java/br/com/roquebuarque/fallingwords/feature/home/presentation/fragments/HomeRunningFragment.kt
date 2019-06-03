package br.com.roquebuarque.fallingwords.feature.home.presentation.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.roquebuarque.fallingwords.R
import kotlinx.android.synthetic.main.fragment_home_running.*
import android.graphics.Point
import android.view.ViewTreeObserver
import android.view.animation.BounceInterpolator
import br.com.roquebuarque.fallingwords.feature.home.presentation.IntentKey


class HomeRunningFragment : Fragment() {

    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_home_running, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            txtMainWordRunning.text = it.getString(ARG_MAIN_WORD)
            txtTranslationWordRunning.text = it.getString(ARG_TRANSLATION_WORD)

            val time = it.getLong(ARG_TIME)
            timer = object : CountDownTimer(time, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    txtTimeRunning.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    callback(IntentKey.NO_ANSWER)
                }
            }
            timer?.start()


            val display = activity?.windowManager?.defaultDisplay
            val size = Point()
            display?.getSize(size)
            val screenHeight = size.y

            txtTranslationWordRunning.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    txtTranslationWordRunning.animate()
                        .setInterpolator(BounceInterpolator())
                        .translationY(((screenHeight - btnRightRunning.height * 5).toFloat()))
                        .setDuration(time)
                        .start()

                    customProgressRunning.setup(
                        countCorrect = it.getInt(ARG_COUNT_CORRECT),
                        countWrong = it.getInt(ARG_COUNT_WRONG),
                        totalQuestion = it.getInt(ARG_COUNT_TOTAL)
                    )

                    txtTranslationWordRunning.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        btnRightRunning.setOnClickListener {
            timer?.cancel()
            callback(IntentKey.RIGHT)
        }

        btnWrongRunning.setOnClickListener {
            timer?.cancel()
            callback(IntentKey.WRONG)
        }

    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }

    companion object {

        private const val ARG_MAIN_WORD = "main_word"
        private const val ARG_TRANSLATION_WORD = "translation_word"
        private const val ARG_TIME = "running_time"
        private const val ARG_COUNT_CORRECT = "count_correct"
        private const val ARG_COUNT_WRONG = "count_wrong"
        private const val ARG_COUNT_TOTAL = "count_total"

        private lateinit var callback: (Int) -> Unit

        fun newInstance(
            mainWord: String,
            translationWord: String,
            time: Long,
            countCorrect: Int,
            countWrong: Int,
            countTotal: Int,
            callback: (Int) -> Unit
        ): HomeRunningFragment {
            this.callback = callback

            val fragment = HomeRunningFragment()
            val bundle = Bundle().apply {
                putString(ARG_MAIN_WORD, mainWord)
                putString(ARG_TRANSLATION_WORD, translationWord)
                putLong(ARG_TIME, time)
                putInt(ARG_COUNT_CORRECT, countCorrect)
                putInt(ARG_COUNT_WRONG, countWrong)
                putInt(ARG_COUNT_TOTAL, countTotal)
            }

            fragment.arguments = bundle

            return fragment
        }

    }
}