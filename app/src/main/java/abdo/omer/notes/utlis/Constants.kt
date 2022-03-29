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

