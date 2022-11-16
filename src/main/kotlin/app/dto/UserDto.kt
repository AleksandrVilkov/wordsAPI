package app.dto

import app.entity.UserEntity

class UserDto(
    val id: Int,
    val created: String,
    val role: String,
    val status: String,
    val login: String,
    var pass: String
)

fun UserDto.toEntity(): UserEntity = UserEntity(
    id = this.id,
    created = this.created,
    role = this.role,
    status = this.status,
    login = this.login,
    pass = this.pass

)