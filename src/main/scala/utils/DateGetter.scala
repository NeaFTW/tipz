package utils

import java.text.SimpleDateFormat
import java.util.Calendar

/**
  * Created by root on 27/12/16.
  */
class DateGetter {
  def currentDate() : String = {
    val today = Calendar.getInstance().getTime()
    // create the date/time formatters
    val dayFormat = new SimpleDateFormat("dd")
    val monthFormat = new SimpleDateFormat("mm")
    val yearFormat = new SimpleDateFormat("yyyy")

    val currentDay = dayFormat.format(today)
    val currentMonth = monthFormat.format(today)
    val currentYear = yearFormat.format(today)

    currentDay + "/" + currentMonth + "/" + currentYear
  }
}
