package model.service


interface DataBaseConnector {
    fun  save(data: Any): Boolean
    fun read(data: Any): List<Any>
    fun update(data: Any): Boolean
    fun delete(data: Any): Boolean
}