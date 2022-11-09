package app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
class WordsApplication

fun main(args: Array<String>) {
    runApplication<WordsApplication>(*args)
}
