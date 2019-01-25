package com.neiko.smswidget.UI.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.telephony.SmsManager
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.neiko.smswidget.Config
import com.neiko.smswidget.Model.Enum.FubState
import com.neiko.smswidget.Model.Makros
import com.neiko.smswidget.R
import com.neiko.smswidget.Session
import com.neiko.smswidget.UI.View.CustomAppBarLayoutBehavior
import com.neiko.smswidget.UI.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_makros_edit.view.*


class MakrosEditFragment : Fragment() {

    private var id: Int? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_makros_edit, container, false)
        id = arguments?.getInt("item_id")

        if (id != null) {

            // toolbar & fubButton
            (activity as BaseActivity).apply {
                toolbar.title = getString(R.string.fragment_title_makros_edit)
                appbar.apply {
                    setExpanded(false)
                    val layoutParams = getLayoutParams() as CoordinatorLayout.LayoutParams
                    (layoutParams.behavior as CustomAppBarLayoutBehavior).setScrollBehavior(false)
                }

                setFubState(FubState.SEND)
            }

            setHasOptionsMenu(true)

            val makros = Session.instance.getMakrosById(id!!)

            view.makros_edit_phone_input?.setText(makros?.number)
            view.makros_edit_name_input?.setText(makros?.name)
            view.makros_edit_message_input?.setText(makros?.message)

            // MARK: SET *addTextChangedListener() FOR textEdit
            setChangeListeners(view)
        } else {

            // toolbar & fubButton
            (activity as BaseActivity).apply {
                toolbar.title = getString(R.string.fragment_title_makros_add)
                setFubState(FubState.SAVE)
            }
        }


        // MARK: SET *onClickListener() FOR @R.id.fubButton
        (activity as BaseActivity).findViewById<FloatingActionButton>(R.id.fubButton).setOnClickListener {
            val fubState = (activity as BaseActivity).getFubState()

            val makros = Makros(
                id = -1,
                name = view.makros_edit_name_input?.text.toString(),
                number = view.makros_edit_phone_input?.text.toString(),
                message = view.makros_edit_message_input?.text.toString()
            )

            when (fubState) {
                FubState.EDIT -> {
                    makros.id = id!!
                    Session.instance.editMakros(makros)
                }

                FubState.SAVE -> {
                    Session.instance.addMakros(makros)
                }

                FubState.SEND -> {
                    SmsManager.getDefault().sendTextMessage(makros.number, null, makros.message, null, null)
                    Snackbar.make(view, getString(R.string.snackbar_info_message_send), 2000).show()
                }
                else -> {
                    Snackbar.make(view, "=(", 1500).show()
                }
            }
            findNavController().navigateUp()
        }

        // MARK: SET *onClickListener() FOR R.id.add_contact_button
        view?.findViewById<ImageButton>(R.id.add_contact_button)?.setOnClickListener {
            val contactPickerIntent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(contactPickerIntent, Config.AR_GET_CONTACT)
        }

        return view
    }


    fun setChangeListeners(view: View) {
        // MARK: SET *addTextChangedListener() FOR @R.id.*phone_input
        view.makros_edit_phone_input?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                (activity as BaseActivity).setFubState(FubState.EDIT)
            }
        })

        // MARK: SET *addTextChangedListener() FOR @R.id.*name_input
        view.makros_edit_name_input?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                (activity as BaseActivity).setFubState(FubState.EDIT)
            }
        })

        // MARK: SET *addTextChangedListener() FOR @R.id.*message_input
        view.makros_edit_message_input?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                (activity as BaseActivity).setFubState(FubState.EDIT)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        menu?.apply {
            val menuItem = add(resources.getString(R.string.frag_edit_menu_item_delete))
            menuItem.setOnMenuItemClickListener { item ->
                Session.instance.removeMakrosById(id!!)
                view?.let { Snackbar.make(it, resources.getString(R.string.snackbar_info_makros_delete), 1500).show() }
                findNavController().navigateUp()
                return@setOnMenuItemClickListener true
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            // MARK: get result data from contacts
            Config.AR_GET_CONTACT -> if (resultCode == Activity.RESULT_OK) {
                val contactData = data!!.data

                activity?.getContentResolver()?.query(contactData, null, null, null, null)?.let { cur ->
                    if (cur.getCount() > 0) {
                        if (cur.moveToNext()) {
                            val id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                            val name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                            view?.makros_edit_phone_input?.setHelperText(name)

                            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                                val phones = activity?.getContentResolver()?.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null,
                                    null
                                )

                                phones?.let {
                                    if (it.count > 0 && it.moveToFirst()) {
                                        val phoneNumber =
                                            phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                        val filtredNumber = phoneNumber
                                            .trim()
                                            .replace("-", "")
                                            .replace(" ", "")

                                        view?.makros_edit_phone_input?.setText(filtredNumber)
                                    }
                                }
                                phones?.close()
                            }

                        }
                    }
                    cur.close()
                }
            }
        }

    }

}