package model.service

import controller.WordServiceInterface
import logger.Logger
import model.Entity.Message
import model.Entity.Word
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.random.Random


@Service
class WordServiceImpl(
    @Autowired
    private val connector: DataBaseConnector
) : WordServiceInterface {
    val logger = Logger("WordServiceImpl")
    override fun createWord(word: Word, msgs: MutableList<Message>) {
//TODO
    }

    override fun findRandomWord(countLetters: Int, msgs: MutableList<Message>): Word? {
        val result =
            connector.read(
                connector.getProperties().getProperty("wordsTable"),
                "countletters",
                "$countLetters"
            )
        val wordList = mutableListOf<Word>()
        while (result.next()) {
            wordList.add(Word(result.getString("uid"), result.getString("value"), result.getString("countletters")))
        }
        if (wordList.isEmpty()) {
            val msgText = "Получен пустой список слов"
            msgs.add(Message(msgText,  ""))
            logger.debug(msgText)
            return null
        }
        return wordList[Random.nextInt(0, wordList.size)]
    }

    override fun findWord(value: String, msgs: MutableList<Message>): Word? {
        val result =
            connector.read(connector.getProperties().getProperty("wordsTable"),
                "value",
                "'${value.lowercase()}'")

        val wordList = mutableListOf<Word>()
        while (result.next()) {
            wordList.add(Word(result.getString("uid"), result.getString("value"), result.getString("countletters")))
        }
        if (wordList.size != 1) {
            val messageText = "Найдено некоректное количество слов -  ${wordList.size}.  Ожидалось 1.  " +
                    "Искомое слово -  $value"
            msgs.add(Message(messageText, ""))
            logger.debug(messageText)
            return null
        }
        return wordList[0]
    }
}