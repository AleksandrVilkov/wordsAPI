package model.service

import model.Entity.Word
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class WordServiceImpl : WordServiceInterface {
    @Autowired
    private val dbConnector: DataBaseConnector = TODO()
    override fun createWordsInDataBase(words: List<Word>): Boolean {
        return dbConnector.save(words)
    }

    override fun findRandomWord(light: Int): Word {
        TODO("Not yet implemented")
    }

    override fun findWord(value: String): Word {
        TODO("Not yet implemented")
    }
}