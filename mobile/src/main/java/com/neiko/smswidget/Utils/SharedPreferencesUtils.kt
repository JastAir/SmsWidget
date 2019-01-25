package com.neiko.smswidget.Utils

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.common.util.JsonUtils
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.neiko.smswidget.Config
import com.neiko.smswidget.Model.Makros
import android.R.id.edit
import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import android.R.attr.key




class SharedPreferencesUtils(val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(Config.SP_NAME, Context.MODE_PRIVATE)

    private val editor = sharedPreferences.edit()

    fun getAllMakros(): List<Makros>? {

        val fromSh = sharedPreferences.getString(Config.SP_MAKROS_LIST, "")
        if (fromSh != null && fromSh != "") {

            val gson = Gson()
            val list: List<Makros>?

            val type = object : TypeToken<List<Makros>>() {}.type
            list = gson.fromJson(fromSh, type)
            return list
        }
        return null
    }

    @SuppressLint("CommitPrefEdits")
    fun saveAllMakros(items: ArrayList<Makros>) {
        editor.clear()
        setList(Config.SP_MAKROS_LIST, items)
    }


    fun <T> setList(key: String, list: List<T>) {
        val gson = Gson()
        val json = gson.toJson(list)

        set(key, json)
    }

    operator fun set(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }
}

