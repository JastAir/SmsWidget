package com.neiko.smswidget.UI.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.neiko.smswidget.Model.Enum.FubState
import com.neiko.smswidget.Model.Makros
import com.neiko.smswidget.R
import com.neiko.smswidget.Session
import com.neiko.smswidget.UI.View.CustomAppBarLayoutBehavior
import com.neiko.smswidget.UI.activity.BaseActivity
import com.neiko.smswidget.UI.adapter.MakrosListRecyclerViewAdapter
import com.neiko.smswidget.UI.adapter.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_makros_list.view.*

class MakrosListFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_makros_list, container, false)

        // toolbar & FubButton
        val baseActivity = (activity as BaseActivity)
        baseActivity.apply {
            toolbar.title = getString(R.string.fragment_title_makros_list)
            appbar.apply {
                setExpanded(true)
                val layoutParams = getLayoutParams() as CoordinatorLayout.LayoutParams
                (layoutParams.behavior as CustomAppBarLayoutBehavior).setScrollBehavior(true)
            }
            setFubState(FubState.ADD)
        }

        val adapterDataList = Session.instance.makrosList

        val adapter = MakrosListRecyclerViewAdapter(adapterDataList, this)
        view.makros_recycle_list.adapter = adapter
        view.makros_recycle_list.layoutManager = LinearLayoutManager(this.context)

        // FloatingActionButton OnClickListener
        activity?.findViewById<FloatingActionButton>(R.id.fubButton)?.setOnClickListener {
            val state = (activity as BaseActivity).getFubState()
            when (state) {
                FubState.ADD -> {
                    findNavController().navigate(R.id.action_edit)
                }
            }
        }
        return view
    }

    @SuppressLint("ShowToast")
    override fun onListFragmentInteraction(item: Makros, itemView: View) {
        var sharedExtras: FragmentNavigator.Extras? = null
        view?.let {
            sharedExtras = FragmentNavigatorExtras(
                it.findViewById<TextView>(R.id.item_number) to "phone",
                it.findViewById<TextView>(R.id.content) to "message"
            )
        }

        val bundle = Bundle()
        bundle.putSerializable("item_id", item.id)
        findNavController().navigate(R.id.action_edit, bundle, null, sharedExtras)
    }
}
