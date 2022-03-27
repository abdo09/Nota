package abdo.omer.notes.utlis

import abdo.omer.notes.R
import abdo.omer.notes.data.models.DateModel
import abdo.omer.notes.di.getSharedPrefs
import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.LocalDate


class Constants {

    fun firstTime(context: Context): Boolean {
        return getSharedPrefs(context).getBoolean(context.getString(R.string.pref_first_time), false)
    }

    fun firstTime(context: Context, isFirstTime: Boolean) {
        getSharedPrefs(context).edit().putBoolean(context.getString(R.string.pref_first_time), isFirstTime).apply()
    }

    fun getCurrentLanguage(context: Context): String? {
        return getSharedPrefs(context).getString(context.getString(R.string.pref_language), "en")
    }

    fun setCurrentLanguage(context: Context, lang: String) {
        getSharedPrefs(context).edit().putString(context.getString(R.string.pref_language), lang).apply()
    }

    fun getUid(context: Context): String? {
        return getSharedPrefs(context).getString(context.getString(R.string.pref_uid), "")
    }

    fun setUid(context: Context, uid: String) {
        getSharedPrefs(context).edit().putString(context.getString(R.string.pref_uid), uid).apply()
    }

    fun getAccessToken(context: Context): String? {
        return getSharedPrefs(context).getString(context.getString(R.string.pref_access_token), "")
    }

    fun getRefreshToken(context: Context): String? {
        return getSharedPrefs(context).getString(context.getString(R.string.pref_refresh_token), "")
    }

    fun selectedDatePosition(context: Context): Int {
        return getSharedPrefs(context).getInt(context.getString(R.string.pref_selected_date_position), -1)
    }

    fun selectedDatePosition(context: Context, selectedDatePosition: Int?) {
        getSharedPrefs(context).edit().putInt(context.getString(R.string.pref_selected_date_position), selectedDatePosition?: -1).apply()
    }

    fun selectedIcon(context: Context): String? {
        return getSharedPrefs(context).getString(context.getString(R.string.pref_selected_icon), "")
    }

    fun selectedIcon(context: Context, selectedDate: String?) {
        getSharedPrefs(context).edit().putString(context.getString(R.string.pref_selected_icon), selectedDate?: "").apply()
    }

    fun setDate(context: Context, dateModel: DateModel) {
        val gson = Gson()
        val json = gson.toJson(dateModel)
        getSharedPrefs(context).edit().putString(context.getString(R.string.pref_date_model), json).apply()
    }

    fun getDate(context: Context): DateModel? {
        val gson = Gson()
        val json = getSharedPrefs(context).getString(context.getString(R.string.pref_date_model), null)
        val type: Type = object : TypeToken<DateModel?>() {}.type
        return gson.fromJson<Any>(json, type) as DateModel?
    }

    companion object{
        var SELECTED_DATE = -1
    }

}

