package model

import app.model.enumCollectilos.UserRole
import app.model.enumCollectilos.UserStatus
import org.apache.commons.codec.binary.Base64
import org.springframework.security.crypto.bcrypt.BCrypt


fun encode(pass: String): String {
    return BCrypt.hashpw(pass, BCrypt.gensalt())
}

fun isEmptyString(str: String): Boolean {
    return str == "" || str.isEmpty()
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
