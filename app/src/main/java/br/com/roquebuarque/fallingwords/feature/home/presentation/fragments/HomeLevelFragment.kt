package br.com.roquebuarque.fallingwords.feature.home.presentation.fragments

import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.roquebuarque.fallingwords.R
import br.com.roquebuarque.fallingwords.feature.home.presentation.IntentKey
import kotlinx.android.synthetic.main.fragment_home_level.view.*


class HomeLevelFragment : Fragment() {

    private var level = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_level, container, false)

        with(view) {
            btnNextLevel.setOnClickListener {
                callback(level)
            }

            rdEasyLevel.isChecked = true
            rdGroupLevel.setOnCheckedChangeListener { _, _ ->
                when {
                    rdEasyLevel.isChecked -> level = IntentKey.EASY
                    rdMediumLevel.isChecked -> level = IntentKey.MEDIUM
                    rdHardLevel.isChecked -> level = IntentKey.HARD
                }
            }
        }

        return view
    }


    companion object {

        private lateinit var callback: (Int) -> Unit

        fun newInstance(callback: (Int) -> Unit): HomeLevelFragment {
            this.callback = callback
            return HomeLevelFragment()
        }

    }

}