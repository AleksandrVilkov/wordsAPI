package controller

import controller.entityVO.Response
import controller.entityVO.Status
import controller.entityVO.WordVO
import model.service.WordServiceImpl
import model.service.WordServiceInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

@Component
@RestController
@RequestMapping("/api/v1/word")
class WordController(
    @Autowired
    val wordService: WordServiceInterface
) {
    @GetMapping("/random")
    fun getRandomWord(@RequestParam countLetters: Int): Response {
        return if (checkCount(countLetters)) {
            val word = wordService.findRandomWord(countLetters)
            Response(Status.OK, "", word?.let { WordVO(it.wordValue) })
        } else
            Response(Status.ERROR, "The number of letters must be between 3 and 6")
    }

    @GetMapping("/check")
    fun checkWord(@RequestParam  desiredWord: String): Response {
        val word = wordService.findWord(desiredWord)
        return if (word == null) {
            Response(Status.ERROR, "no such word exists", WordVO(desiredWord))
        } else
            Response(Status.OK, "", WordVO(word.wordValue))
    }
}