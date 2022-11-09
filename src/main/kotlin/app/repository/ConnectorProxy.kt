package app.repository

import app.logger.Logger
import app.model.service.DataBaseConnector
import model.Entity.Entity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.*

@Repository
class ConnectorProxy(
    @Autowired
    val connector: PSQLConnector
) : DataBaseConnector {
    val logger = Logger("PSQLConnector")

    override fun save(data: Entity): Boolean {
        val sqlQuery = "INSERT INTO ${data.getTable()}${data.getColumns()} VALUES ${data.getValues()};"
        logger.debug("sending query $sqlQuery to PSQL")
        return connector.sendQueryWithoutResult(sqlQuery)
    }

    override fun read(table: String, keyParams: String, valueParams: String): ResultSet {
        val sqlQuery = "SELECT * FROM $table WHERE $keyParams = $valueParams;"
        logger.debug("sending query $sqlQuery to PSQL")
        return connector.sendQuery(sqlQuery)
    }

    override fun update(
        tableName: String,
        paramsValue: Map<String, String>,
        uidObject: String
    ): Boolean {
        var sqlQuery = "UPDATE $tableName SET "
        var iter = 1
        for (pair in paramsValue) {
            sqlQuery += "${pair.key} = '${pair.value}'"
            if (iter != paramsValue.size)
                sqlQuery += ","

            iter++
        }
        sqlQuery += " WHERE uid = '$uidObject';"
        return connector.sendQueryWithoutResult(sqlQuery)
    }

    override fun delete(data: Entity): Boolean {
        val sqlQuery = "DELETE FROM ${data.getTable()} WHERE uid = ${data.uid}}"
        logger.debug("sending query $sqlQuery to PSQL")
        return connector.sendQueryWithoutResult(sqlQuery)
    }

    override fun getProperties(): Properties {
        return connector.getProperties()
    }
}
