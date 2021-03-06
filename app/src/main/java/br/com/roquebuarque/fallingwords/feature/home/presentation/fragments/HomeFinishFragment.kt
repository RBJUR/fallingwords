package br.com.roquebuarque.fallingwords.feature.home.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import br.com.roquebuarque.fallingwords.R
import kotlinx.android.synthetic.main.fragment_home_finish.view.*


class HomeFinishFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_finish, container, false)
        with(view) {

            arguments?.let {
                txtRightFinish.text = String.format(getString(R.string.total_right), it.getInt(ARG_TOTAL_RIGHT))
                txtWrongFinish.text = String.format(getString(R.string.total_wrong), it.getInt(ARG_TOTAL_WRONG))
                txtTotalFinish.text = String.format(getString(R.string.total_question), it.getInt(ARG_TOTAL_QUESTION))

                lottieViewFinish.setAnimation(if (it.getInt(ARG_TOTAL_QUESTION) / 2 < it.getInt(ARG_TOTAL_RIGHT)) "trophy.json" else "sad.json")
                lottieViewFinish.playAnimation()


                customProgressFinish.viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        customProgressFinish.setup(
                            countCorrect = it.getInt(ARG_TOTAL_RIGHT),
                            countWrong = it.getInt(ARG_TOTAL_WRONG),
                            totalQuestion = it.getInt(ARG_TOTAL_QUESTION)
                        )

                        customProgressFinish.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }

            btnPlayAgainFinish.setOnClickListener {
                callback()
            }
        }

        return view
    }


    companion object {

        private const val ARG_TOTAL_RIGHT = "total_right"
        private const val ARG_TOTAL_WRONG = "total_wrong"
        private const val ARG_TOTAL_QUESTION = "total_question"

        private lateinit var callback: () -> Unit

        fun newInstance(countRight: Int, countWrong: Int, size: Int, callback: () -> Unit): HomeFinishFragment {
            this.callback = callback

            val fragment = HomeFinishFragment()

            val bundle = Bundle().apply {
                putInt(ARG_TOTAL_RIGHT, countRight)
                putInt(ARG_TOTAL_WRONG, countWrong)
                putInt(ARG_TOTAL_QUESTION, size)
            }

            fragment.arguments = bundle

            return fragment
        }

    }
}