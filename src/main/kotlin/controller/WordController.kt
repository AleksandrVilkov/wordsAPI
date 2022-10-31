package controller

import controller.entityVO.Response
import controller.entityVO.Word
import model.service.WordServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Component
@RestController
@RequestMapping("/api/v1/word")
class WordController(
    @Autowired
    val wordServiceImpl: WordServiceImpl
) {
    @GetMapping("/random")
    fun getRandomWord(): Response {
        val wordVO = Word(wordServiceImpl.findRandomWord(5).wordValue)
        return Response("", "", wordVO)
    }

    @GetMapping("/check")
    fun checkWord(): Response {
        val wordVO = Word(wordServiceImpl.findWord("Собака").wordValue)
        return Response("", "", wordVO)
    }

    @GetMapping("/guessed")
    fun getGuessedWord(): Response {
        return Response("", "")
    }

}