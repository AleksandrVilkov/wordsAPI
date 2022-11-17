package app.entity

import app.dto.GameDto
import javax.persistence.*

@Entity
@Table(name = "games")
class GameEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val created: String = "",
    val userId: Int,
    var updated: String = "",
    var status: String = "",
    var time: String = "",
    var hiddenWord: String = "",
    var countLettersInHiddenWord: Int = 0,
    var countAttempts: Int = 0,
    val attemptWords: String = ""
)

fun GameEntity.toDto():GameDto = GameDto(
    id = this.id,
    created = this.created,
    userId = this.userId,
    status = this.status,
    countLettersInHiddenWord = this.countLettersInHiddenWord,
    hiddenWord = this.hiddenWord,
    countAttempts = this.countAttempts
)