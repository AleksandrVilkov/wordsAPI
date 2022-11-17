package app.entity

import app.dto.WordDto
import javax.persistence.*

@Entity
@Table(name = "words")
class WordEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    val id: Int = 0,
    val wordValue: String = "",
    val countLetters: Int = 0,
)

fun WordEntity.toDto(): WordDto = WordDto(
    value = this.wordValue
)