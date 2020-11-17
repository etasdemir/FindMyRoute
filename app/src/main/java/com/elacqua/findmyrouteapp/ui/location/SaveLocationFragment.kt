package com.elacqua.findmyrouteapp.ui.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.elacqua.findmyrouteapp.R
import com.elacqua.findmyrouteapp.util.FragmentState
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.save_location_fragment.*

@AndroidEntryPoint
class SaveLocationFragment : Fragment() {

    private val viewModel: SaveLocationViewModel by viewModels()
    private var state = FragmentState.EDITABLE_STATE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStateFromArguments()
        if (state == FragmentState.EDITABLE_STATE){
            initEditableState()
        } else {
            initNotEditableState()
        }
        buttonCancelListener()
    }

    private fun setStateFromArguments() {
        val argKey = getString(R.string.fragment_state)
        val newState = arguments?.get(argKey) ?: FragmentState.EDITABLE_STATE
        state = newState as FragmentState
    }

    private fun initEditableState() {
        val argKey = getString(R.string.save_location_key)
        val location = arguments?.get(argKey)
        location?.let {
            buttonSaveListener(location as LatLng)
        }
    }

    private fun buttonSaveListener(location: LatLng) {
        btn_save_location_save.setOnClickListener {
            val title = txt_save_location_title.text.toString()
            val description = txt_save_location_description.text.toString()
            viewModel.saveLocation(title, description, location)
            navigateBack()
        }
    }

    private fun buttonCancelListener() {
        btn_save_location_cancel.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        hideKeyboard()
        parentFragmentManager.popBackStack()
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    private fun initNotEditableState() {
        val tempName = "Temp Name"
        val tempDescription = "Temp Description"
        txt_save_location_title.run {
            setText(tempName)
            isEnabled = false
        }
        txt_save_location_description.run {
            setText(tempDescription)
            isEnabled = false
        }
        btn_save_location_save.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.save_location_fragment, container, false)
    }

}