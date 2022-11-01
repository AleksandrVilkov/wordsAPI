package controller.entityVO


data class Response(
    val status: Status,
    val description: String,
    val data: EntityVO? = null
)