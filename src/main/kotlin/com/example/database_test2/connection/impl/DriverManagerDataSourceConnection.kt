package com.example.database_test2.connection.impl

import com.example.database_test2.connection.DataSourceConnection
import com.example.database_test2.connection.DatabaseConnection
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.DriverManager

@Component("driverManager")
class DriverManagerDataSourceConnection : DataSourceConnection {
    private val logger = LoggerFactory.getLogger(DatabaseConnection::class.java)
    private val url = "jdbc:h2:tcp://localhost/~/db_practice"
    private val username = "sa"
    private val password = ""

    override fun getConnection(): Connection {
        val connection = DriverManager.getConnection(url, username, password)
        logger.info("connection = $connection, class = ${connection::class.java}")

        return connection
    }
}