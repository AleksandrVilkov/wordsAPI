package app

import app.entity.WordEntity
import app.repository.WordRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.File
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class RepositoryTest {

    @Autowired
    private val wr: WordRepository? = null

    init {
        println("${Date()} Start test Repository")
    }
    @Test
    fun addWordsInDataBase() {
        val file = File("src/main/resources/text/allWords.txt")
        val wordsValues = file.readLines()
        val words = mutableListOf<WordEntity>()
        for (wordValue in wordsValues) {
            words.add(WordEntity(wordValue = wordValue, countLetters = wordValue.length))
        }

        wr?.saveAll(words)
    }

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
        println(identifyRightWords(words = words, suitableLetters = suitableLetters, excludeLetters = excludeLetters))
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

    private fun identifyRightWords(
        words: List<String>?,
        suitableLetters: Map<String, List<Int>>,
        excludeLetters: Set<String>
    ): List<String> {
        val result = mutableListOf<String>()
        //Находим подходящие слова
        if (words != null) {
            for (word in words) {
                var thisWordIsFit = true
                for (pair in suitableLetters) {
                    if (!thisWordIsFit) continue
                    val wordLow = word.lowercase()
                    val latter = pair.key.lowercase()
                    val wordsContains = wordLow.indexOf(latter) + 1 //+1 для правильного учета индексов
                    if (wordsContains == 0 || !pair.value.contains(wordsContains)) {
                        thisWordIsFit = false
                    }
                }
                //Проверяем не подходящие буквы
                if (thisWordIsFit) {
                    for (letter in excludeLetters) {
                        if (word.contains(letter)) {
                            thisWordIsFit = false
                        }
                    }
                }
                if (thisWordIsFit)
                    result.add(word)
            }
        }
        return result
    }
}