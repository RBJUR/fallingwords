package br.com.roquebuarque.fallingwords.feature.home.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.roquebuarque.fallingwords.R
import br.com.roquebuarque.fallingwords.feature.home.presentation.IntentKey
import kotlinx.android.synthetic.main.fragment_home_level.*


class HomeLevelFragment : Fragment() {

    private var level = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_home_level, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    companion object {

        private lateinit var callback: (Int) -> Unit

        fun newInstance(callback: (Int) -> Unit): HomeLevelFragment {
            this.callback = callback
            return HomeLevelFragment()
        }

    }

}