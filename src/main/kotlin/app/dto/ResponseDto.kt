package app.dto

data class ResponseDto (
    val status: Status,
    val description: String,
    val data: EntityVO? = null
        )