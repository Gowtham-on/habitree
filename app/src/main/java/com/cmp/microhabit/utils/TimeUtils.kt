package com.cmp.microhabit.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

object TimeUtils {
    fun getDateRangeLastSundayToThisSaturday(pattern: String, noOfDaysBefore: Int): List<String> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)

        val lastSundayCal = cal.clone() as Calendar
        lastSundayCal.add(Calendar.DAY_OF_YEAR, -noOfDaysBefore)

        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        val result = mutableListOf<String>()

        val tempCal = lastSundayCal.clone() as Calendar
        while (!tempCal.after(cal)) {
            result.add(sdf.format(tempCal.time))
            tempCal.add(Calendar.DAY_OF_YEAR, 1)
        }
        return result
    }

    fun isToday(dateString: String): Boolean {
        val today = Calendar.getInstance()
        return today.get(Calendar.DAY_OF_MONTH) == dateString.toInt()
    }

    fun getYesterday(pattern: String): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun getToday(pattern: String): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun getCurrentMonthYear(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter =
                java.time.format.DateTimeFormatter.ofPattern("MMM yyyy", Locale.getDefault())
            val currentDate = LocalDate.now()
            currentDate.format(formatter)
        } else {
            val sdf = SimpleDateFormat("MMM yyyy", Locale.getDefault())
            val currentDate = Calendar.getInstance().time
            sdf.format(currentDate)
        }
    }
}