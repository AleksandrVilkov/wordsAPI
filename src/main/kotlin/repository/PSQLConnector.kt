package model.repository

import model.logger.Logger
import org.postgresql.util.PSQLException
import repository.DataBaseConnector
import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.util.*

class PSQLConnector {

    val props: Properties
    private val filePath = "src/main/resources/psql.properties"
    private val logger = Logger("PSQLConnector")
    private lateinit var connection: Connection

    init {
        val file = FileInputStream(filePath)
        val properties = Properties()
        properties.load(file)
        props = properties
        if (!::connection.isInitialized)
            connection = DriverManager.getConnection(getJDBCURL(), getLogin(), getPass())
    }


    fun sendQuery(sqlQuery: String): ResultSet {
        val query = getConnector().prepareStatement(sqlQuery)
        logger.debug(sqlQuery)
        return query.executeQuery()
    }

    fun sendQueryWithoutResult(sqlQuery: String) {
        val query = getConnector().prepareStatement(sqlQuery)
        try {
            logger.debug(sqlQuery)
            query.executeQuery()
        } catch (e: PSQLException) {
            e.message?.let { logger.warn(it) }
        }
    }

    private fun getConnector(): Connection {
        return connection
    }

    private fun getJDBCURL(): String {
        val file = FileInputStream(filePath)
        val props = Properties()
        props.load(file)
        return props.getProperty("url") + props.getProperty("host") + props.getProperty("port") + props.getProperty("dataBaseName")
    }

    private fun getLogin(): String {
        return props.getProperty("login")
    }

    private fun getPass(): String {
        return props.getProperty("pass")
    }

}