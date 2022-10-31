package model.dataBase

import model.Entity.Entity
import model.Entity.Word
import model.repository.PSQLConnector
import model.service.DataBaseConnector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConnectorProxy(
    @Autowired
    val connector: PSQLConnector
) : DataBaseConnector {
    override fun save(data: Entity) {
        val sqlQuery = "INSERT INTO ${data.getTable()}${data.getColumn()} VALUES ${data.getValue()};"
        connector.sendQueryWithoutResult(sqlQuery)
    }

    //TODO
    override fun read(data: Entity, whereParams: String): Entity {
        val result = connector.sendQuery("SELECT * FROM ${data.getTable()} WHERE $whereParams")
        val entityList = mutableListOf<Entity>()
        while (result.next()) {
            result.getString("")

        }
        return Word("4","5")
    }

    override fun update(data: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(data: Entity): Boolean {
        TODO("Not yet implemented")
    }
}
