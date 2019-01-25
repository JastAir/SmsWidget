package com.neiko.smswidget

import android.annotation.SuppressLint
import android.content.Context
import com.neiko.smswidget.Model.Makros
import com.neiko.smswidget.Utils.SharedPreferencesUtils
import java.util.*

object Session {
    @SuppressLint("StaticFieldLeak")
    val instance = Session()


    class Session {

        var makrosList: ArrayList<Makros> = ArrayList()
        var selfContext: Context? = null


        fun setupContext(applicationContext: Context?) {
            selfContext = applicationContext

            updateMakrosList()
        }

        fun updateMakrosList() {
            val dbList = SharedPreferencesUtils(selfContext!!).getAllMakros()
            dbList?.let {
                makrosList = ArrayList(it)
            }
        }

        fun getMakrosById(id: Int): Makros? {
            makrosList.forEach {
                if (it.id == id) return it
            }
            return null
        }

        fun removeMakrosById(id: Int) {
            val iterator = makrosList.iterator()
            while (iterator.hasNext()) {
                val value = iterator.next()
                if (value.id == id) {
                    iterator.remove()
                }
            }
            makrosList.trimToSize()

            SharedPreferencesUtils(selfContext!!).saveAllMakros(makrosList)
        }

        fun editMakros(item: Makros) {
            val iterator = makrosList.iterator()
            while (iterator.hasNext()) {
                val value = iterator.next()
                if (value.id == item.id) {
                    iterator.remove()
                }
            }
            makrosList.add(item)

            SharedPreferencesUtils(selfContext!!).saveAllMakros(makrosList)
        }

        fun addMakros(item: Makros) {
            val lastMakros = makrosList.maxBy { makros -> makros.id }
            item.id = lastMakros?.id?.plus(1) ?: 0
            makrosList.add(item)

            SharedPreferencesUtils(selfContext!!).saveAllMakros(makrosList)
        }


    }
}


