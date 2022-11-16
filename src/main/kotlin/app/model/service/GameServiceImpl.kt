package app.model.service

import app.controller.GameServiceInterface
import app.controller.WordServiceInterface
import app.dto.GameDto
import app.dto.MessageDto
import app.dto.toEntity
import app.entity.toDto
import app.logger.Logger
import app.model.enumCollectilos.GameStatus
import app.model.enumCollectilos.LetterStatus
import app.repository.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class GameServiceImpl(
    @Autowired private val gameRepository: GameRepository,
    @Autowired private val wordService: WordServiceInterface,
) : GameServiceInterface {
    val logger = Logger("GameServiceImpl")

    override fun createGame(game: GameDto, msgs: MutableList<MessageDto>): GameDto {
        val word = wordService.findRandomWord(game.countLettersInHiddenWord, msgs)
        game.hiddenWord = word?.wordValue.toString()
        if (game.hiddenWord.isEmpty()) {
            val messageTest = "Ошибка получения случайного слова длинной ${game.countLettersInHiddenWord}"
            msgs.add(
                MessageDto(messageTest)
            )
            logger.debug(messageTest)
            game.status = GameStatus.ERROR.name
        }
        gameRepository.save(game.toEntity())
        return game
    }

    override fun foundUserGameInGame(userId: Int, gameId: Int, msgs: MutableList<MessageDto>): GameDto? {
        val gamesEntity = gameRepository.findByUserId(userId)
        val result = mutableListOf<GameDto>()
        for (game in gamesEntity) {
            if (game.status.equals(GameStatus.IN_GAME.name)) result.add(game.toDto())
        }

        if (result.size != 1) {
            msgs.add(MessageDto("Ошибка получения игры. Найдено более 1 игры в статусе \"В игре\""))
            return null
        } else return result[0]
    }

    private fun getAttemptWordsFromString(string: String): MutableList<String> {
        val array = string.split(", ")
        val result = mutableListOf<String>()
        for (str in array) {
            result.add(str)
        }
        return result
    }

    override fun attemptResult(
        userId: Int,
        gameId: Int,
        attemptWord: String,
        msgs: MutableList<MessageDto>,
        result: MutableMap<String, LetterStatus>
    ) {
        if (wordService.findWord(attemptWord, msgs) != null) {
            val userGame = foundUserGameInGame(userId, gameId, msgs) ?: throw RuntimeException("Game not found")
            if (userGame.hiddenWord.length != attemptWord.length) throw RuntimeException("Word length does not match")
            val attemptArray = attemptWord.toCharArray()
            for (attemptLetter in attemptArray) {
                if (!userGame.hiddenWord.contains(attemptLetter.lowercase())) {
                    result[attemptLetter.toString()] = LetterStatus.MISSING
                } else {
                    val hiddenIndex = userGame.hiddenWord.indexOf(attemptLetter)
                    val attemptIndex = attemptWord.indexOf(attemptLetter)
                    val status: LetterStatus
                    if (hiddenIndex == attemptIndex) status = LetterStatus.GUESSED
                    else status = LetterStatus.IS_ELSEWHERE
                    result[attemptLetter.toString()] = status
                }
            }
            userGame.countAttempts += 1
            userGame.updated = LocalDate.now().toString()
            userGame.attemptWords.add(attemptWord)
            updateGames(game = userGame, msgs = msgs)
        }

    }

    override fun readUserGames(userId: Int, msgs: MutableList<MessageDto>): List<GameDto> {
        val result = gameRepository.findByUserId(userId)
        val games = mutableListOf<GameDto>()
        return result.map {
            it.toDto()
        }
    }

    override fun updateGames(game: GameDto, msgs: MutableList<MessageDto>): Boolean {
        //TODO implements
        return false;
    }
}