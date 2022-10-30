package model.dataBase

import java.sql.ResultSet

interface DataBaseConnector {
    fun sendQuery(sqlQuery: String): ResultSet
    fun sendQueryWithoutResult(sqlQuery: String)
}