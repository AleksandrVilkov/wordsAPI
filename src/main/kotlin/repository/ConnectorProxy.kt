package model.dataBase

import model.service.DataBaseConnector
import org.springframework.stereotype.Component

@Component
class ConnectorProxy: DataBaseConnector {
    override fun save(data: Any): Boolean {
        TODO("Not yet implemented")
    }

    override fun read(data: Any): List<Any> {
        TODO("Not yet implemented")
    }

    override fun update(data: Any): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(data: Any): Boolean {
        TODO("Not yet implemented")
    }
}