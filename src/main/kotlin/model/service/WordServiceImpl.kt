package model.service

import model.Entity.Word
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class WordServiceImpl(
    @Autowired
    private val dbConnector: DataBaseConnector
) : WordServiceInterface {
    override fun createWordsInDataBase(word: Word) {
        dbConnector.save(word)
    }

    override fun findRandomWord(light: Int): Word {
        TODO("Not yet implemented")
    }

    override fun findWord(value: String): Word {
        TODO("Not yet implemented")
    }
}