package controllers.entityVO


data class Response(
    val status: String,
    val description: String,
    val data: EntityVO? = null
)