package controller.entityVO

import model.Entity.UserRole
import model.Entity.UserStatus
import java.util.*

enum class Status {
    OK, ERROR
}

interface EntityVO {
}
class WordVO(val value: String) : EntityVO
class UserVO(
    val created: String,
    val role: String,
    val status: String,
    val login: String,
    ): EntityVO