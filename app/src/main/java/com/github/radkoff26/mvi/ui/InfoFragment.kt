package com.github.radkoff26.mvi.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.github.radkoff26.mvi.R
import com.github.radkoff26.mvi.databinding.FragmentInfoBinding
import com.github.radkoff26.mvi.extensions.getStore
import com.github.radkoff26.mvi.store.StateCallback
import com.github.radkoff26.mvi.user.UserState

class InfoFragment : Fragment(), OnClickListener {
    private var _binding: FragmentInfoBinding? = null
    private val binding: FragmentInfoBinding
        get() = _binding!!

    private val stateCallback = StateCallback<UserState> {
        binding.updateUserInfo(it)
        Log.d(TAG, "state updated")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.bind(
            inflater.inflate(R.layout.fragment_info, container, false)
        )

        binding.setupBinding()

        getStore().subscribe(viewLifecycleOwner, stateCallback)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onClick(v: View?) {
        closeFragment()
    }

    private fun closeFragment() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            remove(this@InfoFragment)
            commit()
        }
    }

    private fun FragmentInfoBinding.setupBinding() {
        leaveFragmentButton.setOnClickListener(this@InfoFragment)
        updateUserInfo(getStore().getCurrentState())
    }

    private fun FragmentInfoBinding.updateUserInfo(userState: UserState) {
        with(userState) {
            idInfo.text = this.id.toString()
            nicknameInfo.text = this.nickname
            latitudeInfo.text = this.location.latitude.toString()
            longitudeInfo.text = this.location.longitude.toString()
        }
    }

    companion object {
        const val TAG = "TAG_InfoFragment"
    }
}