package br.com.roquebuarque.fallingwords.feature.home.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.roquebuarque.fallingwords.R
import kotlinx.android.synthetic.main.fragment_home_result.view.*


class HomeResultFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_home_result, container, false)

        with(view) {
            arguments?.let {
                resultView.setAnimation(if (it.getBoolean(ARG_RESULT, false)) "success.json" else "error.json")
                resultView.playAnimation()
            }

            Handler().postDelayed({
                callback()
            }, 2000)
        }

        return view
    }


    companion object {
        private const val ARG_RESULT = "answer_result"
        private lateinit var callback: () -> Unit

        fun newInstance(result: Boolean, callback: () -> Unit): HomeResultFragment {
            this.callback = callback

            val fragment = HomeResultFragment()
            val bundle = Bundle().apply {
                putBoolean(ARG_RESULT, result)
            }

            fragment.arguments = bundle

            return fragment
        }

    }
}