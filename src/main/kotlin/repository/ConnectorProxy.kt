package model.dataBase

import logger.Logger
import model.Entity.Entity
import model.repository.PSQLConnector
import model.service.DataBaseConnector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.util.*

@Component
class ConnectorProxy(
    @Autowired
    val connector: PSQLConnector
) : DataBaseConnector {
    val logger = Logger("PSQLConnector")

    override fun save(data: Entity) {
        val sqlQuery = "INSERT INTO ${data.getTable()}${data.getColumns()} VALUES ${data.getValues()};"
        logger.debug("sending query $sqlQuery to PSQL")
        connector.sendQueryWithoutResult(sqlQuery)
    }

    override fun read(table: String, keyParams: String, valueParams: String): ResultSet {
        val sqlQuery = "SELECT * FROM $table WHERE $keyParams = $valueParams;"
        logger.debug("sending query $sqlQuery to PSQL")
        return connector.sendQuery(sqlQuery)
    }

    override fun update(data: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(data: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun getProperties(): Properties {
        return connector.getProperties()
    }
}
