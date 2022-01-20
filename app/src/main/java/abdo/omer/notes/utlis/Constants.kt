package abdo.omer.notes.utlis

import abdo.omer.notes.R
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


    fun setAccessToken(context: Context, accessToken: String?) {
        getSharedPrefs(context).edit().putString(context.getString(R.string.pref_access_token), accessToken).apply()
    }

    fun getRefreshToken(context: Context): String? {
        return getSharedPrefs(context).getString(context.getString(R.string.pref_refresh_token), "")
    }

    fun setRefreshToken(context: Context, refreshToken: String?) {
        getSharedPrefs(context).edit().putString(context.getString(R.string.pref_refresh_token), refreshToken).apply()
    }

    fun selectedDate(context: Context): Int {
        return getSharedPrefs(context).getInt(context.getString(R.string.pref_selected_date), -1)
    }

    fun selectedDate(context: Context, selectedDate: Int?) {
        getSharedPrefs(context).edit().putInt(context.getString(R.string.pref_selected_date), selectedDate?: -1).apply()
    }

    fun selectedIcon(context: Context): String? {
        return getSharedPrefs(context).getString(context.getString(R.string.pref_selected_icon), "")
    }

    fun selectedIcon(context: Context, selectedDate: String?) {
        getSharedPrefs(context).edit().putString(context.getString(R.string.pref_selected_icon), selectedDate?: "").apply()
    }

    companion object{
        var SELECTED_DATE = -1
    }

}

