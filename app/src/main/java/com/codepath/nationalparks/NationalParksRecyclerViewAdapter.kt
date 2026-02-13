package com.codepath.nationalparks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.ImageView


/**
 * [RecyclerView.Adapter] that can display a [NationalPark] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class NationalParksRecyclerViewAdapter(
    private val parks: List<NationalPark>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<NationalParksRecyclerViewAdapter.ParkViewHolder>() {

    // Inflate the item layout from XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_national_park, parent, false)
        return ParkViewHolder(view)
    }

    // ViewHolder class holds references to all UI elements inside the list item layout
    inner class ParkViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: NationalPark? = null

        // TODO: Step 4a - Add references for remaining views from XML
        val mParkName: TextView = mView.findViewById(R.id.park_name)
        val mParkLocation: TextView = mView.findViewById(R.id.park_location)
        val mParkDescription: TextView = mView.findViewById(R.id.park_description)
        val mParkImage: ImageView = mView.findViewById(R.id.park_image)

        override fun toString(): String {
            return mParkName.toString() + " '" + mParkDescription.text + "'"
        }
    }

    override fun onBindViewHolder(holder: ParkViewHolder, position: Int) {
        val park = parks[position]

        holder.mItem = park  // (important if you're using holder.mItem for clicks)

        holder.mParkName.text = park.name
        holder.mParkLocation.text = park.location
        holder.mParkDescription.text = park.description

        // Apply expanded/collapsed UI state
        holder.mParkDescription.maxLines = if (park.isExpanded) Int.MAX_VALUE else 4
        holder.mParkDescription.ellipsize =
            if (park.isExpanded) null else android.text.TextUtils.TruncateAt.END

        // Tap description to expand/collapse
        holder.mParkDescription.setOnClickListener {
            park.isExpanded = !park.isExpanded
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                notifyItemChanged(pos)
            }
        }

        Glide.with(holder.mView)
            .load(park.imageUrl)
            .centerCrop()
            .into(holder.mParkImage)

        // Keep your existing item click behavior
        holder.mView.setOnClickListener {
            mListener?.onItemClick(park)
        }
    }


    // Tells the RecyclerView how many items to display
    override fun getItemCount(): Int {
        return parks.size
    }
}
