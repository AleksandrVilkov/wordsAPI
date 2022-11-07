package model.service

import controller.GameServiceInterface
import controller.WordServiceInterface
import logger.Logger
import model.Entity.Game
import model.GameStatus
import model.LetterStatus
import model.Entity.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class GameServiceImpl(
    @Autowired
    private val dbConnector: DataBaseConnector,
    private val wordService: WordServiceInterface
) : GameServiceInterface {
    val logger = Logger("GameServiceImpl")

    override fun createGame(game: Game, msgs: MutableList<Message>): Game? {
        val word = wordService.findRandomWord(game.countLettersInHiddenWord, msgs)
        game.hiddenWord = word?.wordValue.toString()
        if (game.hiddenWord.isEmpty()) {
            val messageTest = "Ошибка получения случайного слова длинной ${game.countLettersInHiddenWord}"
            msgs.add(
                Message(messageTest, "")
            )
            logger.debug(messageTest)
            game.status = GameStatus.ERROR
        }
        return if (dbConnector.save(game)) {
            game
        } else {
            val text = "Не удалось создать игру для пользователя  ${game.userUid}"
            msgs.add(Message(text, ""))
            logger.debug(text)
            null
        }
    }

    override fun foundUserGameInGame(userUid: String, gameUid: String, msgs: MutableList<Message>): Game? {
        val result = dbConnector.read(dbConnector.getProperties().getProperty("gamesTable"), "uid", "'$gameUid'")
        val games = mutableListOf<Game>()
        while (result.next()) {
            val gameStatus = defineGameStatus(result.getString("status"))
            val userUidFromGame = result.getString("useruid")
            if (gameStatus.equals(GameStatus.IN_GAME) && userUidFromGame.equals(userUid)) {
                games.add(
                    Game(
                        uid = result.getString("uid"),
                        created = LocalDate.parse(result.getString("created")),
                        userUid = userUidFromGame,
                        updated = LocalDate.parse(result.getString("updated")),
                        status = gameStatus,
                        time = result.getString("time"),
                        hiddenWord = result.getString("hiddenWord"),
                        countLettersInHiddenWord = result.getString("hiddenWord").length,
                        attemptWords = getAttemptWordsFromString(result.getString("attemptWords")),
                        countAttempts = result.getInt("countAttempts")
                    )
                )
            }
        }
        if (games.size != 1) {
            msgs.add(Message("Ошибка получения игры. Найдено более 1 игры в статусе \"В игре\"", ""))
            return null
        } else return games[0]
    }

    private fun getAttemptWordsFromString(string: String): MutableList<String> {
        val array = string.split(", ")
        val result = mutableListOf<String>()
        for (str in array) {
            result.add(str)
        }
        return result
    }


    //возможно будет нужна мапа позиция буквы - мапа буква статус
    override fun attemptResult(
        userUid: String,
        gameUid: String,
        attemptWord: String,
        msgs: MutableList<Message>,
        result: MutableMap<String, LetterStatus>
    ) {
        if (wordService.findWord(attemptWord, msgs) != null) {
            val userGame = foundUserGameInGame(userUid, gameUid, msgs) ?: throw RuntimeException("Game not found")
            if (userGame.hiddenWord.length != attemptWord.length)
                throw RuntimeException("Word length does not match")
            val attemptArray = attemptWord.toCharArray()
            for (attemptLetter in attemptArray) {
                if (!userGame.hiddenWord.contains(attemptLetter.lowercase())) {
                    result[attemptLetter.toString()] = LetterStatus.MISSING
                } else {
                    val hiddenIndex = userGame.hiddenWord.indexOf(attemptLetter)
                    val attemptIndex = attemptWord.indexOf(attemptLetter)
                    val status: LetterStatus
                    if (hiddenIndex == attemptIndex)
                        status = LetterStatus.GUESSED
                    else status = LetterStatus.IS_ELSEWHERE
                    result[attemptLetter.toString()] = status
                }
            }
            userGame.countAttempts += 1
            userGame.updated = LocalDate.now()
            userGame.attemptWords.add(attemptWord)
            updateGames(game = userGame, msgs = msgs)
        }

    }

    override fun readUserGames(userUid: String, msgs: MutableList<Message>): List<Game> {
        val result = dbConnector.read(dbConnector.getProperties().getProperty("gamesTable"), "useruid", "'$userUid'")
        val games = mutableListOf<Game>()
        while (result.next()) {
            games.add(
                Game(
                    created = LocalDate.parse(result.getString("created")),
                    userUid = result.getString("useruid"),
                    updated = LocalDate.parse(result.getString("updated")),
                    status = defineGameStatus(result.getString("status")),
                    time = result.getString("time"),
                    hiddenWord = result.getString("hiddenWord"),
                    countLettersInHiddenWord = result.getString("hiddenWord").length,
                    countAttempts = result.getInt("countAttempts")
                )
            )
        }
        return games
    }

    override fun updateGames(game: Game, msgs: MutableList<Message>): Boolean {
        val tableName = dbConnector.getProperties().getProperty("gamesTable")
        val values = mapOf<String, String>(
            "status" to game.status.name,
            "countattempts" to game.countAttempts.toString(),
            "updated" to LocalDate.now().toString()
        )
        return dbConnector.update(tableName = tableName, paramsValue = values, uidObject = game.uid)
    }


    private fun defineGameStatus(statusString: String): GameStatus {
        return when (statusString) {
            GameStatus.IN_GAME.name -> GameStatus.IN_GAME
            GameStatus.NOT_IN_GAME.name -> GameStatus.NOT_IN_GAME
            GameStatus.ERROR.name -> GameStatus.ERROR
            GameStatus.FINISHED.name -> GameStatus.FINISHED
            else -> GameStatus.NOT_DETERMINED
        }
    }
}