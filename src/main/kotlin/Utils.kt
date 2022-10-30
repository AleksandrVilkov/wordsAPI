package model

import org.apache.commons.codec.binary.Base64


fun decode(encodePass: String): String {
    val base64 = Base64()
    return String(base64.decode(encodePass.toByteArray()), Charsets.UTF_8)
}

fun encode(pass: String): String {
    val base64 = Base64()
    return String(base64.encode(pass.toByteArray()), Charsets.UTF_8)
}