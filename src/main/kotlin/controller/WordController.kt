package controller

import controller.entityVO.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/word")
class WordController {
    @GetMapping("/random")
    fun getRandomWord(): Response {
        return Response("", "")
    }

    @GetMapping("/check")
    fun checkWord(): Response {
        return Response("", "")
    }

    @GetMapping("/guessed")
    fun getGuessedWord(): Response {
        return Response("", "")
    }

}