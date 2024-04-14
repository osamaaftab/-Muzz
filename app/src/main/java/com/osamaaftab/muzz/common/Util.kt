package com.osamaaftab.muzz.common

import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.random.Random

object Util {

    fun <T> getRandomItem(list: List<T>): T? {
        if (list.isEmpty()) return null // Return null if the list is empty
        val randomIndex = Random.nextInt(list.size) // Generate a random index
        return list[randomIndex] // Return the element at the random index
    }

    fun getCurrentTimeFormatted(time: Long): String {
        val dateFormat = SimpleDateFormat("EEEE HH:mm", Locale.getDefault())
        return dateFormat.format(time)
    }


}