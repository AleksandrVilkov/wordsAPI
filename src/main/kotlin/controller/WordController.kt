package controller

import controller.entityVO.Response
import controller.entityVO.Status
import controller.entityVO.WordVO
import model.Entity.Message
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
        val msgs = mutableListOf<Message>()
        val correctCountLetters = checkCount(countLetters)
        val word = wordService.findRandomWord(countLetters, msgs)
        if (msgs.isEmpty() && correctCountLetters) {
            Response(Status.OK, "", word?.let { WordVO(it.wordValue) })
        }
        return Response(Status.ERROR, getDescription(msgs))
    }

    @GetMapping("/check")
    fun checkWord(@RequestParam desiredWord: String): Response {
        val msgs = mutableListOf<Message>()
        val word = wordService.findWord(desiredWord, msgs)
        return if (word != null && msgs.isEmpty()) {
            Response(Status.OK, "", WordVO(word.wordValue))
        } else {
            Response(Status.ERROR, getDescription(msgs), WordVO(desiredWord))
        }
    }
}