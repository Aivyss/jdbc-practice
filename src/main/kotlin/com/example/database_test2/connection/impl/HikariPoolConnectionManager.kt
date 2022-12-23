package com.example.database_test2.connection.impl

import com.example.database_test2.connection.ConnectionContext
import com.example.database_test2.connection.ConnectionManager
import com.example.database_test2.connection.DataSourceConnection
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.sql.Connection

@Component("hikariManager")
class HikariPoolConnectionManager(
    @Qualifier("hikariDC") private val dataSourceConnection: DataSourceConnection,
    private val connectionContext: ConnectionContext,
) : ConnectionManager {

    init {
        connectionContext.setConnection(dataSourceConnection.getConnection())
    }

    override fun getConnection(): Connection {
        return connectionContext.getConnection()
    }

    override fun close() {
        connectionContext.getConnection().close()
    }
}