package model

import model.Entity.UserRole
import model.Entity.UserStatus
import org.apache.commons.codec.binary.Base64
import org.springframework.security.crypto.bcrypt.BCrypt
import java.time.LocalDate


fun encode(pass: String): String {
    val base64 = Base64()
    return BCrypt.hashpw(pass,BCrypt.gensalt())
}

fun defineUserRole(roleString: String): UserRole {
    return when (roleString) {
        UserRole.USER.name -> UserRole.USER
        UserRole.ADMIN.name -> UserRole.ADMIN
        UserRole.MODERATOR.name -> UserRole.MODERATOR
        else -> UserRole.NOT_DETERMINED
    }
}

fun defineUserStatus(statusString: String): UserStatus {
    return when (statusString) {
        UserStatus.ACTIVE.name -> UserStatus.ACTIVE
        UserStatus.DELETED.name -> UserStatus.DELETED
        UserStatus.NOT_ACTIVE.name -> UserStatus.NOT_ACTIVE
        else -> UserStatus.NOT_DETERMINED

    }
}

//fun convertStringToDate(string: String):LocalDate {
//
//}
