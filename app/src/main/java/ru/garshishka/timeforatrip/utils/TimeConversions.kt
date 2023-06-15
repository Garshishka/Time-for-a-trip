package ru.garshishka.timeforatrip.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun getTimeGlobal(datetime: String) : String{
    val formatterGotten = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z z")
    val formatterForApp = DateTimeFormatter.ofPattern("HH:mm:ss yyyy.MM.dd Z z")
    return ZonedDateTime.parse(datetime, formatterGotten).format(formatterForApp)
}

fun getTimeLocal(datetime: String) : String{
    val formatterGotten = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z z")
    val formatterForApp = DateTimeFormatter.ofPattern("HH:mm:ss yyyy.MM.dd")
    return ZonedDateTime.parse(datetime, formatterGotten).withZoneSameInstant(
        ZoneId.of("Etc/GMT-3")).format(formatterForApp)
}