package br.com.roquebuarque.fallingwords.feature.home.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.roquebuarque.fallingwords.R
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
            //TODO CREATE KEYS FOR LEVEL VALUE
            when {
                rdEasyLevel.isChecked -> level = 1
                rdMediumLevel.isChecked -> level = 2
                rdHardLevel.isChecked -> level = 3
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