package app.model.service

import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.BufferedWriter
import java.io.FileWriter

@Service
class Cron {
    @Scheduled(fixedDelay = 86_400_000) //Раз в сутки
    @Async
    fun clearLog() {
        val fileWriter = FileWriter("temp/logs/log.log", false)
        val bufferWriter = BufferedWriter(fileWriter)
        bufferWriter.write("")
        bufferWriter.close()
    }
}