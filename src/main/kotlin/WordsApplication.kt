import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDate

@SpringBootApplication
class WordsApplication

fun main(args: Array<String>) {
	println(LocalDate.now())
	runApplication<WordsApplication>(*args)
}
