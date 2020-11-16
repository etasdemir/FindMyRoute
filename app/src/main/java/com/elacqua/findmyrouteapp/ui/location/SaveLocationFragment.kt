package com.elacqua.findmyrouteapp.ui.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.elacqua.findmyrouteapp.R
import kotlinx.android.synthetic.main.save_location_fragment.*
import timber.log.Timber

class SaveLocationFragment : Fragment() {

    private val viewModel: SaveLocationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSaveListener()
        buttonCancelListener()
    }

    private fun buttonSaveListener() {
        btn_save_location_save.setOnClickListener {
            Timber.e("Save location")
            navigateBack()
        }
    }

    private fun buttonCancelListener() {
        btn_save_location_cancel.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() = parentFragmentManager.popBackStack()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.save_location_fragment, container, false)
    }

}