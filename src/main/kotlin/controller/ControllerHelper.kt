package controller

import model.Entity.Game
import model.Entity.GameStatus
import model.Entity.Message

fun checkCount(count: Int): Boolean {
    //TODO вынести в настройки
    return count in 4..6
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