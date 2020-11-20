package com.elacqua.findmyrouteapp.ui.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.findmyrouteapp.R
import com.elacqua.findmyrouteapp.data.local.entity.Place
import kotlinx.android.synthetic.main.rv_item.view.*

class PlaceRecyclerAdapter(
    private val placeSelectedListener: OnPlaceSelectedListener
) : RecyclerView.Adapter<PlaceRecyclerAdapter.PlaceViewHolder>() {

    private val places = ArrayList<Place>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_item, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.run {
            onBind(position)
            onClick(position)
        }
    }

    override fun getItemCount() =
        places.size

    fun clearAndAddPlaces(placesList: List<Place>) {
        places.clear()
        places.addAll(placesList)
        notifyDataSetChanged()
    }

    fun getPlaces() = places

    inner class PlaceViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var isExpanded = false

        fun onBind(position: Int) {
            view.txt_rv_item_title.text = places[position].title
            view.expandLayout.visibility = View.GONE
        }

        fun onClick(position: Int) {
            view.setOnClickListener {
                toggle()
            }
            handleDetailButton(position)
            handleShowLocationButton(position)
        }

        private fun toggle() {
            if (isExpanded) {
                collapse()
            } else {
                expand()
            }
            isExpanded = !isExpanded
        }

        private fun collapse() {
            view.expandLayout.visibility = View.GONE
        }

        private fun expand() {
            view.expandLayout.visibility = View.VISIBLE
        }

        private fun handleDetailButton(position: Int) {
            view.btn_expand_show_detail.setOnClickListener {
                placeSelectedListener.onDetailClicked(places[position])
            }
        }

        private fun handleShowLocationButton(position: Int) {
            view.btn_expand_show_location.setOnClickListener {
                placeSelectedListener.onLocationClicked(places[position])
            }
        }
    }
}