package app.model.service

import model.Entity.Entity
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.util.*



interface DataBaseConnector {
    fun save(data: Entity): Boolean
    fun read(table: String, keyParams: String, valueParams: String): ResultSet
    fun update(tableName: String, paramsValue: Map<String, String>, uidObject: String): Boolean
    fun delete(data: Entity): Boolean
    fun getProperties(): Properties
}