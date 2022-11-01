package model.service

import model.Entity.Word
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


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
            wordList.add(Word(result.getString("wordValue"), result.getString("countletters")))
        }
        if (wordList.isEmpty()) {
            return null
        }
        //TODO расчитывать рандомное значение
        return wordList[1]
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

        return Word("", "")
    }
}