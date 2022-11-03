package logger

import java.io.File
import java.io.PrintWriter
import java.util.*

class Logger(
    private var className: String,
) {
    val filePath = "/temp/logs/log.log"
    fun info(msg: String) {
        showAndSaveLogs("INFO: $className ${Date()} $msg")
    }

    fun debug(msg: String) {
        showAndSaveLogs("DEBUG: $className ${Date()} $msg")
    }

    fun error(msg: String) {
        showAndSaveLogs("ERROR: $className ${Date()} $msg")
    }

    fun warn(msg: String) {
        showAndSaveLogs("WARN!: $className ${Date()} $msg")
    }

    private fun showAndSaveLogs(msg: String) {
        val file = File(filePath)
        val writer = PrintWriter(file)
        writer.use { it.append("$msg\n") }
        println(msg)
    }
}