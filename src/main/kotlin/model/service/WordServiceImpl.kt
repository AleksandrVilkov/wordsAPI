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

    override fun findRandomWord(light: Int): Word {
        val result =
            connector.read(connector.getProperties().getProperty("wordsTable"), "countletters", "$light")
        val wordList = mutableListOf<Word>()
        while (result.next()) {
            wordList.add(Word(result.getString("wordValue"), result.getString("countletters")))
        }
        //TODO расчитывать рандомное значение
        return wordList[1]
    }

    override fun findWord(value: String): Word {
        TODO("Not yet implemented")
    }
}