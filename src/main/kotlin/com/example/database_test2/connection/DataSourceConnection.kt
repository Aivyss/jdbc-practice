package com.example.database_test2.connection

import org.springframework.jdbc.support.JdbcUtils
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface DataSourceConnection {
    fun getConnection(): Connection
    fun close(pstmt: PreparedStatement, conn: Connection, rs: ResultSet? = null) {
        JdbcUtils.closeResultSet(rs)
        JdbcUtils.closeStatement(pstmt)
        JdbcUtils.closeConnection(conn)
    }
}