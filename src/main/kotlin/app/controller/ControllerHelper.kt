package app.controller

import app.dto.GameDto
import app.dto.MessageDto
import app.model.enumCollectilos.GameStatus
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

fun getDescription(msgs: List<MessageDto>): String {
    var description = ""
    for (msg in msgs) {
        description += "${msg.messageTest}; "
    }
    return description
}

fun canStartGame(games: List<GameDto>, msgs: MutableList<MessageDto>): Boolean {
    var count = 0
    for (game in games) {
        if (game.status.equals(GameStatus.IN_GAME.name)) {
            msgs.add(
                MessageDto(
                    "Уже существует игра в статусе ${GameStatus.IN_GAME}: " +
                            "id: ${game.id}," +
                            " создана: ${game.created}, " +
                            "загаданное слово: ${game.hiddenWord}"
                )
            )
            count++
        }

    }
    return count == 0

}