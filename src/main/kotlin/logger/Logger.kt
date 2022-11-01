package logger

import java.util.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

//TODO  + сохранять в файл
class Logger(
    private var className: String,
) : LoggerInterface {

    override fun info(msg: String) {
        println("INFO: $className ${Date()} $msg")
    }

    override fun debug(msg: String) {
        println("DEBUG: $className ${Date()} $msg")
    }

    override fun error(msg: String) {
        println("ERROR: $className ${Date()} $msg")
    }

    override fun warn(msg: String) {
        println("$className ${Date()} $msg")
    }

    override fun setNameClass(name: String) {
        this.className = name
    }
}