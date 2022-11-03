package model.repository

import logger.Logger
import org.postgresql.util.PSQLException
import org.springframework.stereotype.Component
import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.util.*

@Component
class PSQLConnector {

    private final val props: Properties
    private val filePath = "src/main/resources/application.properties"
    private lateinit var connection: Connection
    val logger = Logger("PSQLConnector")

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

    fun sendQueryWithoutResult(sqlQuery: String): Boolean {
        val query = getConnector().prepareStatement(sqlQuery)
        try {
            logger.debug(sqlQuery)
            query.executeQuery()
            return true
        } catch (e: PSQLException) {
            e.message?.let {
                if (it.contains("Запрос не вернул результатов")) {
                    return true
                } else {
                    logger.warn(it)
                    return false
                }
            }


            return false
        }
    }

    fun getProperties(): Properties {
        return props
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