import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication(scanBasePackages = ["src/main/kotlin"])
class WordsApplication

fun main(args: Array<String>) {
    runApplication<WordsApplication>(*args)
}
