package com.example.database_test2.connection

import java.sql.Connection

interface ConnectionManager {
    fun setConnection(conn: Connection)
    fun getConnection(): Connection
}