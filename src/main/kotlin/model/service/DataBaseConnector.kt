package model.service

import model.Entity.Entity


interface DataBaseConnector {
    fun save(data: Entity)
    fun read(data: Entity, whereParams: String): Entity
    fun update(data: Entity): Boolean
    fun delete(data: Entity): Boolean
}