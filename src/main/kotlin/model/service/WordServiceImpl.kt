package model.service

import model.Entity.Word
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.random.Random


@Component
class WordServiceImpl(
    @Autowired
    private val connector: DataBaseConnector
) : WordServiceInterface {
    override fun createWord(word: Word) {

    }

    override fun findRandomWord(countLetters: Int): Word? {
        val result =
            connector.read(
                connector.getProperties().getProperty("wordsTable"),
                "countletters",
                "$countLetters"
            )
        val wordList = mutableListOf<Word>()
        while (result.next()) {
            wordList.add(Word(result.getString("value"), result.getString("countletters")))
        }
        if (wordList.isEmpty()) {
            return null
        }
        return wordList[Random.nextInt(0, wordList.size)]
    }

    override fun findWord(value: String): Word? {
        val result =
            connector.read(connector.getProperties().getProperty("wordsTable"), "value", "'${value.lowercase()}'")
        val wordList = mutableListOf<Word>()
        while (result.next()) {
            wordList.add(Word(result.getString("value"), result.getString("countletters")))
        }
        //TODO проверять полностью слово на совпадения, исключая варианты с вхождением
        if (wordList.isNotEmpty())
            return wordList.get(0)

        return null
    }
}