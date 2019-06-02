package br.com.roquebuarque.fallingwords.feature.home.presentation.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.roquebuarque.fallingwords.R
import kotlinx.android.synthetic.main.fragment_home_runningv2.*
import android.graphics.Point
import android.view.ViewTreeObserver
import android.view.animation.BounceInterpolator
import timber.log.Timber


class HomeRunningFragment : Fragment() {

    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_home_runningv2, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtMainWordRunning.text = arguments?.getString(ARG_MAIN_WORD)
        txtTranslationWordRunning.text = arguments?.getString(ARG_TRANSLATION_WORD)

        val time = arguments?.getLong(ARG_TIME)
        time?.let {
            timer = object : CountDownTimer(time, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    txtTimeRunning.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    callback(3)
                }
            }
            timer?.start()


            val display = activity?.windowManager?.defaultDisplay
            val size = Point()
            display?.getSize(size)
            val screenHeight = size.y

            txtTranslationWordRunning.viewTreeObserver.addOnGlobalLayoutListener(object:
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    txtTranslationWordRunning.animate()
                        .setInterpolator(BounceInterpolator())
                        .translationY(((screenHeight - btnRightRunning.height * 4).toFloat()))
                        .setDuration(time)
                        .start()

                    txtTranslationWordRunning.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }



        btnRightRunning.setOnClickListener {
            timer?.cancel()
            callback(2)
        }

        btnWrongRunning.setOnClickListener {
            timer?.cancel()
            callback(1)
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

        private lateinit var callback: (Int) -> Unit

        fun newInstance(
            mainWord: String,
            translationWord: String,
            time: Long,
            callback: (Int) -> Unit
        ): HomeRunningFragment {
            this.callback = callback

            val fragment = HomeRunningFragment()
            val bundle = Bundle().apply {
                putString(ARG_MAIN_WORD, mainWord)
                putString(ARG_TRANSLATION_WORD, translationWord)
                putLong(ARG_TIME, time)
            }

            fragment.arguments = bundle

            return fragment
        }

    }
}