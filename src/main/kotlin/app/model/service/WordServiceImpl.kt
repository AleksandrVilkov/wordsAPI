package app.model.service

import app.controller.WordServiceInterface
import app.dto.MessageDto
import app.dto.WordDto
import app.dto.toEntity
import app.entity.toDto
import app.logger.Logger
import app.repository.WordRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.random.Random


@Service
class WordServiceImpl(
    @Autowired
    private val wordRepository: WordRepository
) : WordServiceInterface {
    val logger = Logger("WordServiceImpl")
    override fun createWord(word: WordDto, msgs: MutableList<MessageDto>) {
        val res = wordRepository.save(word.toEntity())
    }

    override fun findRandomWord(countLetters: Int, msgs: MutableList<MessageDto>): WordDto? {
        val result = wordRepository.findByCountletters(countLetters)
        val wordList = result.map {
            it.toDto()
        }
        if (wordList.isEmpty()) {
            val msgText = "Получен пустой список слов"
            msgs.add(MessageDto(msgText))
            logger.debug(msgText)
            return null
        }
        return wordList[Random.nextInt(0, wordList.size)]
    }

    override fun findWord(value: String, msgs: MutableList<MessageDto>): WordDto? {
        val result = wordRepository.findByValue(value)

        val wordList = result.map {
            it.toDto()
        }
        if (wordList.size != 1) {
            val messageText = "Найдено некоректное количество слов -  ${wordList.size}.  Ожидалось 1.  " +
                    "Искомое слово -  $value"
            msgs.add(MessageDto(messageText))
            logger.debug(messageText)
            return null
        }
        return wordList[0]
    }
}