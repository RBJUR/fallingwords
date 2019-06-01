package br.com.roquebuarque.fallingwords.feature.home.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.roquebuarque.fallingwords.R
import kotlinx.android.synthetic.main.fragment_home_running.*

class HomeRunningFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_home_running, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtMainWordRunning.text = arguments?.getString(ARG_MAIN_WORD)
        txtTranslationWordRunning.text = arguments?.getString(ARG_TRANSLATION_WORD)

        btnRightRunning.setOnClickListener {
            callback(2)
        }

        btnWrongRunning.setOnClickListener {
            callback(1)
        }
    }

    companion object {

        private const val ARG_MAIN_WORD = "main_word"
        private const val ARG_TRANSLATION_WORD = "translation_word"

        private lateinit var callback: (Int) -> Unit

        fun newInstance(mainWord: String, translationWord: String, callback: (Int) -> Unit): HomeRunningFragment {
            this.callback = callback

            val fragment = HomeRunningFragment()
            val bundle = Bundle().apply {
                putString(ARG_MAIN_WORD, mainWord)
                putString(ARG_TRANSLATION_WORD, translationWord)
            }

            fragment.arguments = bundle

            return fragment
        }

    }
}