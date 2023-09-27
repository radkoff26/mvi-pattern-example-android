package com.github.radkoff26.mvi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.radkoff26.mvi.R
import com.github.radkoff26.mvi.action.UserAction
import com.github.radkoff26.mvi.databinding.FragmentChangeNameBinding
import com.github.radkoff26.mvi.extensions.app
import com.github.radkoff26.mvi.extensions.getStore
import com.github.radkoff26.mvi.interfaces.DefaultTextWatcher
import com.github.radkoff26.mvi.user.UserStore

class ChangeNameFragment : Fragment(), OnClickListener {
    private var _binding: FragmentChangeNameBinding? = null
    private val binding: FragmentChangeNameBinding
        get() = _binding!!

    private var lastText: String = ""

    private val textWatcher = object : DefaultTextWatcher {

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val currentText = s.toString()
            if (currentText != lastText) {
                getStore().dispatch(
                    UserAction.NicknameChange(currentText)
                )
                lastText = currentText
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeNameBinding.bind(
            inflater.inflate(R.layout.fragment_change_name, container, false)
        )

        binding.setupBinding()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onClick(v: View?) {
        v ?: return
        when (v.id) {
            R.id.actualize -> {
                val text = getStore().getCurrentState().nickname
                binding.editText.setText(text)
            }
            R.id.randomizeId -> {
                getStore().dispatch(UserAction.RandomizeId)
            }
        }
    }

    private fun FragmentChangeNameBinding.setupBinding() {
        editText.addTextChangedListener(textWatcher)
        actualize.setOnClickListener(this@ChangeNameFragment)
        randomizeId.setOnClickListener(this@ChangeNameFragment)
    }
}
