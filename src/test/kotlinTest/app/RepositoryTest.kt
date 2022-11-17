package kotlinTest.app

import app.controller.WordServiceInterface
import app.repository.WordRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class RepositoryTest {

    @Autowired
    private val wr: WordRepository? = null

    @Test
    fun guessingAlgorithm() {
        /*Буквы, которые подходят, с индексами, где они могут находиться в слове (Начало расчета с единицы) */
        val suitableLetters = mapOf(
            "р" to listOf(1, 5),
            "ж" to listOf(1, 2, 3),
            "а" to listOf(4)

        )
        /*Слова, использованные в качестве попытки. Не более 5*/
        val attempts = listOf("время", "гараж")
        val excludeLetters = defineExcludedLetters(words = attempts, suitableLetters = suitableLetters.keys)
        val words = getWordsFromDb()
    //    println(identifyRightWords(words = words, suitableLetters = suitableLetters, excludeLetters = excludeLetters))
    }

    private fun defineExcludedLetters(words: List<String>, suitableLetters: Set<String>): Set<String> {
        val excludedLetters = mutableSetOf<String>()
        for (word in words) {
            val array = word.toCharArray()
            for (char in array) {
                excludedLetters.add(char.toString())
            }
        }
        for (suitableLetter in suitableLetters) {
            if (excludedLetters.contains(suitableLetter))
                excludedLetters.remove(suitableLetter)
        }
        return excludedLetters
    }

    private fun getWordsFromDb(): List<String>? {
        return wr?.findByCountLetters(5)?.map { it.wordValue }
    }
}