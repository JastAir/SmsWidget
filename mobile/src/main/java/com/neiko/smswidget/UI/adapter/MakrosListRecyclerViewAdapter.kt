package com.neiko.smswidget.UI.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.neiko.smswidget.Model.Makros
import com.neiko.smswidget.R
import com.neiko.smswidget.Utils.BitmapUtils


import kotlinx.android.synthetic.main.fragment_makroslist_item.view.*

interface OnListFragmentInteractionListener {
    fun onListFragmentInteraction(item: Makros, itemView: View)
}

class MakrosListRecyclerViewAdapter(
    private val mValues: ArrayList<Makros>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MakrosListRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener = View.OnClickListener { v ->
        val item = v.tag as Makros
        mListener?.onListFragmentInteraction(item, v)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_makroslist_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.number
        holder.mNameView.text = item.name
        holder.mContentView.text = item.message

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mNameView: TextView = mView.item_name
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
