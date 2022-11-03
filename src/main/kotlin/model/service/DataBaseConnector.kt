package model.service

import model.Entity.Entity
import java.sql.ResultSet
import java.util.*


interface DataBaseConnector {
    fun save(data: Entity):Boolean
    fun read(table: String, keyParams: String, valueParams: String): ResultSet
    fun update(data: Entity): Boolean
    fun delete(data: Entity): Boolean
    fun getProperties(): Properties
}