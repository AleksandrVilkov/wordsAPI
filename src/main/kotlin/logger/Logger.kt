package logger

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.util.*

class Logger(
    private var className: String,
) {
    val filePath = "temp/logs/log.log"
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
        val fileWriter = FileWriter(filePath, true)
        val bufferWriter = BufferedWriter(fileWriter)
        bufferWriter.write("$msg\n")
        bufferWriter.close()
        println(msg)
    }
}