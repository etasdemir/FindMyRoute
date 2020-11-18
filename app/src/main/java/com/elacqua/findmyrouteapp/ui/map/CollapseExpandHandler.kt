package com.elacqua.findmyrouteapp.ui.map

import android.view.View

class CollapseExpandHandler {
    private var lastIndex = -1

    fun toggle(view: View, position: Int) {
        if (lastIndex == position){
            collapse(view)
        } else {
            expand(view)
        }
        lastIndex = position
    }

    private fun collapse(view: View) {
        view.visibility = View.GONE
    }

    private fun expand(view: View) {
        view.visibility = View.VISIBLE
    }


}