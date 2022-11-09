package app.controller

import app.model.enumCollectilos.GameStatus
import model.Entity.Game
import model.Entity.Message
import org.springframework.beans.factory.annotation.Value

fun checkCount(
    count: Int,
    @Value("min.count.in.word")
    min: Int,
    @Value("max.count.in.word")
    max: Int
): Boolean {
    return count in min..max
}

fun getDescription(msgs: List<Message>): String {
    var description = ""
    for (msg in msgs) {
        description += "${msg.textMessage}; "
    }
    return description
}

fun canStartGame(games: List<Game>, msgs: MutableList<Message>): Boolean {
    var count = 0
    for (game in games) {
        if (game.status.equals(GameStatus.IN_GAME)) {
            msgs.add(
                Message(
                    "Уже существует игра в статусе ${GameStatus.IN_GAME}: " +
                            "uid: ${game.uid}," +
                            " создана: ${game.created}, " +
                            "загаданное слово: ${game.hiddenWord}",
                    ""
                )
            )
            count++
        }

    }
    return count == 0

}