package app.entity


import app.dto.UserDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import kotlin.math.log

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val created: String = "",
    val role: String = "",
    val status: String ="",
    val login: String = "",
    var pass: String = ""
)

fun UserEntity.toDto(): UserDto = UserDto(
    id = this.id,
    created = this.created,
    role = this.role,
    status = this.status,
    login = this.login,
    pass = this.pass
)