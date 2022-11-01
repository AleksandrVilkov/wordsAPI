package logger

import org.springframework.stereotype.Component
import java.util.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

//TODO  + сохранять в файл
class Logger(
    private var className: String,
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