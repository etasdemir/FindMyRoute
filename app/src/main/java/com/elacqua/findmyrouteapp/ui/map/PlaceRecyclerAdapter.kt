package com.elacqua.findmyrouteapp.ui.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.findmyrouteapp.R
import com.elacqua.findmyrouteapp.data.local.model.Place
import kotlinx.android.synthetic.main.rv_item.view.*

class PlaceRecyclerAdapter(
    private val placeSelectedListener: OnPlaceSelectedListener
) : RecyclerView.Adapter<PlaceRecyclerAdapter.PlaceViewHolder>() {

    private val places = ArrayList<Place>()
    private val handler = CollapseExpandHandler()

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

    inner class PlaceViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(position: Int) {
            view.txt_rv_item_title.text = places[position].title
            view.expandLayout.visibility = View.GONE
        }

        fun onClick(position: Int) {
            view.setOnClickListener {
                handler.toggle(view.expandLayout, position)
            }
            buttonDetailListener(position)
            buttonShowLocationListener(position)
        }

        private fun buttonDetailListener(position: Int) {
            view.btn_expand_show_detail.setOnClickListener {
                placeSelectedListener.onDetailClicked(places[position])
            }
        }

        private fun buttonShowLocationListener(position: Int) {
            view.btn_expand_show_location.setOnClickListener {
                placeSelectedListener.onLocationClicked(places[position])
            }
        }
    }
}