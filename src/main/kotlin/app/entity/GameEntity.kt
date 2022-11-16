package app.entity

import app.model.enumCollectilos.GameStatus
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "games")
class GameEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uid: Int = 0,
    val created: String = "",
    val userId: String = "",
    var updated: String = "",
    var status: String = "",
    var time: String = "",
    var hiddenWord: String = "",
    var countLettersInHiddenWord: Int = 0,
    var countAttempts: Int = 0,
    val attemptWords: String = ""
)