package app.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "words")
class WordEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val wordValue: String = "",
    val countLetters: String = "",
)