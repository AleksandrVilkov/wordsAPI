package model.dataBase

import java.sql.ResultSet
import java.util.*

class DataBaseProxyConnector {
    private lateinit var connector: PSQLConnector

    init {
        if (!::connector.isInitialized)
            connector = PSQLConnector()
    }

    fun getProperties(): Properties {
        return connector.props
    }

    fun sendQuery(sqlQuery: String): ResultSet {
        return connector.sendQuery(sqlQuery)
    }

    fun sendQueryWithoutResult(sqlQuery: String) {
        return connector.sendQueryWithoutResult(sqlQuery)
    }
}