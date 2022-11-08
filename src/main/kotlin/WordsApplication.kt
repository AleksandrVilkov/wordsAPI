import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(scanBasePackages = ["src/main/kotlin"], exclude = arrayOf(SecurityAutoConfiguration::class))
class WordsApplication

fun main(args: Array<String>) {
    runApplication<WordsApplication>(*args)
}
