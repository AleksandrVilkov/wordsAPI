package model

import model.Entity.UserRole
import model.Entity.UserStatus
import org.apache.commons.codec.binary.Base64
import java.time.LocalDate


fun decode(encodePass: String): String {
    val base64 = Base64()
    return String(base64.decode(encodePass.toByteArray()), Charsets.UTF_8)
}

fun encode(pass: String): String {
    val base64 = Base64()
    return String(base64.encode(pass.toByteArray()), Charsets.UTF_8)
}

fun defineUserRole(roleString: String): UserRole {
    return when (roleString) {
        UserRole.ROLE_USER.name -> UserRole.ROLE_USER
        UserRole.ROLE_ADMIN.name -> UserRole.ROLE_ADMIN
        UserRole.ROLE_MODERATOR.name -> UserRole.ROLE_MODERATOR
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
