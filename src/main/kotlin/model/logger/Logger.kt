package model.logger

import java.util.*

class Logger(
    val className: String
) {
    fun info(msg: String) {
        println("INFO: $className ${Date()} $msg")
    }

    fun debug(msg: String) {
        println("DEBUG: $className ${Date()} $msg")
    }

    fun error(msg: String) {
        println("ERROR: $className ${Date()} $msg")
    }

    fun warn(msg: String) {
        println("$className ${Date()} $msg")
    }
}