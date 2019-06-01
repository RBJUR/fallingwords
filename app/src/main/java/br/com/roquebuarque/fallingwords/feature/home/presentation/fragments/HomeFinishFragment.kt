package br.com.roquebuarque.fallingwords.feature.home.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.roquebuarque.fallingwords.R
import kotlinx.android.synthetic.main.fragment_home_finish.*

class HomeFinishFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_home_finish, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPlayAgainFinish.setOnClickListener {
            callback()
        }
    }

    companion object {

        private lateinit var callback: () -> Unit

        fun newInstance(callback: () -> Unit): HomeFinishFragment {
            this.callback = callback
            return HomeFinishFragment()
        }

    }
}