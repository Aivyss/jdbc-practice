package com.example.database_test2.connection

import org.springframework.stereotype.Component
import java.sql.Connection

@Component
class ConnectionContext {
    private val threadLocal: ThreadLocal<Connection> = ThreadLocal()

    fun getConnection(): Connection = threadLocal.get()
    fun setConnection(connection: Connection) = threadLocal.set(connection)
}