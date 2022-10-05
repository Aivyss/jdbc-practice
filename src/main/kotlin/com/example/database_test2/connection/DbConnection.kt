package com.example.database_test2.connection

import com.example.database_test2.connection.DatabaseConfig.password
import com.example.database_test2.connection.DatabaseConfig.url
import com.example.database_test2.connection.DatabaseConfig.username
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.LoggerFactory
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.support.JdbcUtils
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import javax.sql.DataSource

object DatabaseConfig {
    val url = "jdbc:h2:tcp://localhost/~/db_practice"
    val username = "sa"
    val password = ""
}

object DatabaseConnection {
    private val logger = LoggerFactory.getLogger(DatabaseConnection::class.java)

    fun getConnectionWithDriverManager(): Connection {
        val connection = DriverManager.getConnection(url, username, password)
        logger.info("connection = $connection, class = ${connection::class.java}")
        return connection
    }

    fun getConnectionWithDriverManagerDataSource(): Connection {
        val ds: DataSource = DriverManagerDataSource(url, username, password)
        val connection = ds.connection
        logger.info("connection = $connection, class = ${connection::class.java}")

        return connection
    }

    fun getConnectionWithHikariConnectionPool(): Connection {
        val ds = HikariDataSource()
        ds.jdbcUrl = url
        ds.username = username
        ds.password = password
        ds.maximumPoolSize = 10
        ds.poolName = "custom connection pool"

        val connection = ds.connection
        logger.info("connection = $connection, class = ${connection::class.java}")
        Thread.sleep(1000)

        return connection
    }

    fun closeV0(pstmt: PreparedStatement, conn: Connection, rs: ResultSet? = null) {
        pstmt.close()
        conn.close()
        rs?.close()
    }
}

interface DatabaseClosable {
    fun close(pstmt: PreparedStatement, conn: Connection, rs: ResultSet? = null) {
        JdbcUtils.closeResultSet(rs)
        JdbcUtils.closeStatement(pstmt)
        JdbcUtils.closeConnection(conn)
    }
}



