package com.tyom.model

import android.content.Context
import java.lang.reflect.Type
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

object Preferences {
    private const val NAME = "preferences"

    fun <T> putObject(context: Context, key: String?, `object`: T) {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        val sharedPreferencesEditor = sharedPreferences.edit()
        val gson = Gson()
        val serializedObject: String = gson.toJson(`object`)
        sharedPreferencesEditor.putString(key, serializedObject)
        sharedPreferencesEditor.apply()
    }

    fun <T> getObject(context: Context, key: String?, type: Class<T>?): T? {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        if (sharedPreferences.contains(key)) {
            return Gson().fromJson<T>(sharedPreferences.getString(key, ""), type)
        }
        return null
    }

    fun <T> putList(context: Context, key: String?, list: ArrayList<T>?) {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        val sharedPreferencesEditor = sharedPreferences.edit()
        val gson = Gson()
        val serializedObject: String = gson.toJson(list)
        sharedPreferencesEditor.putString(key, serializedObject)
        sharedPreferencesEditor.apply()
    }

    fun <T> restoreList(context: Context, key: String?, list: ArrayList<T>, type: Class<T>?) {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        if (sharedPreferences.contains(key)) {
            list.clear()
            val collectionType: Type = com.google.gson.reflect.TypeToken.getParameterized(
                ArrayList::class.java, type
            ).getType()
            list.addAll(
                Gson()
                    .fromJson(sharedPreferences.getString(key, ""), collectionType)
            )
        }
    }

    fun putFilesData(context: Context, key: String?, map: HashMap<String?, String?>?) {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        val sharedPreferencesEditor = sharedPreferences.edit()
        val builder = GsonBuilder()
        val gson =
            builder.enableComplexMapKeySerialization().setPrettyPrinting().create()
        val type: Type = object : com.google.gson.reflect.TypeToken<HashMap<String?, String?>?>() {
        }.getType()
        val json: String = gson.toJson(map, type)
        sharedPreferencesEditor.putString(key, json)
        sharedPreferencesEditor.apply()
    }

    fun restoreFilesData(context: Context, key: String?): HashMap<String, String> {
        val map = HashMap<String, String>()
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        if (sharedPreferences.contains(key)) {
            val collectionType: Type =
                object : com.google.gson.reflect.TypeToken<HashMap<String?, String?>?>() {
                }.getType()
            map.putAll(
                Gson().fromJson(
                    sharedPreferences.getString(key, ""),
                    collectionType
                )
            )
        }
        return map
    }

    fun putString(context: Context, key: String?, value: String?) {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        val sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putString(key, value)
        sharedPreferencesEditor.apply()
    }

    fun getString(context: Context?, key: String?): String? {
        if (context != null) {
            val sharedPreferences = context.getSharedPreferences(NAME, 0)
            return sharedPreferences.getString(key, "")
        } else {
            return ""
        }
    }

    fun getString(context: Context?, key: String?, def: String?): String? {
        if (context != null) {
            val sharedPreferences = context.getSharedPreferences(NAME, 0)
            return sharedPreferences.getString(key, def)
        } else {
            return ""
        }
    }

    fun putBoolean(context: Context, key: String?, value: Boolean) {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        val sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putBoolean(key, value)
        sharedPreferencesEditor.apply()
    }

    fun getBoolean(context: Context, key: String?): Boolean {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        return sharedPreferences.getBoolean(key, false)
    }

    fun getBoolean(context: Context, key: String?, defaultValue: Boolean): Boolean {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun clear(context: Context, key: String?) {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        sharedPreferences.edit().remove(key).apply()
    }

    fun existKey(context: Context, key: String?): Boolean {
        return context.getSharedPreferences(NAME, 0).contains(key)
    }

    fun putInteger(context: Context, key: String?, value: Int) {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        val sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putInt(key, value)
        sharedPreferencesEditor.apply()
    }

    fun getInteger(context: Context, key: String?): Int {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        return sharedPreferences.getInt(key, -1)
    }

    fun getInteger(context: Context, key: String?, defValue: Int): Int {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        return sharedPreferences.getInt(key, defValue)
    }

    fun putFloat(context: Context, key: String?, value: Float) {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        val sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putFloat(key, value)
        sharedPreferencesEditor.apply()
    }

    fun getFloat(context: Context, key: String?): Float {
        val sharedPreferences = context.getSharedPreferences(NAME, 0)
        return sharedPreferences.getFloat(key, -1.0f)
    }
}