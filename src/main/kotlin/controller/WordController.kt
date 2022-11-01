package controller

import controller.entityVO.Response
import controller.entityVO.Status
import controller.entityVO.WordVO
import model.service.WordServiceImpl
import model.service.WordServiceInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Component
@RestController
@RequestMapping("/api/v1/word")
class WordController(
    @Autowired
    val wordService: WordServiceInterface
) {
    @GetMapping("/random/numberletters/{count}")
    fun getRandomWord(@PathVariable count: Int): Response {
        return if (checkCount(count)) {
            val word = wordService.findRandomWord(count)
            Response(Status.OK, "", word?.let { WordVO(it.wordValue) })
        } else
            Response(Status.ERROR, "The number of letters must be between 3 and 6")
    }

    @GetMapping("/check/{desiredWord}")
    fun checkWord(@PathVariable  desiredWord: String): Response {
        val word = wordService.findWord(desiredWord)
        return if (word == null) {
            Response(Status.ERROR, "no such word exists", WordVO(desiredWord))
        } else
            Response(Status.OK, "", WordVO(word.wordValue))
    }
}