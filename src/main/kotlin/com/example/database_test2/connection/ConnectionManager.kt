package com.example.database_test2.connection

import java.sql.Connection

interface ConnectionManager{
    fun getConnection(): Connection
    fun close()
}