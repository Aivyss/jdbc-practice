package com.example.database_test2.connection.impl

import com.example.database_test2.connection.DataSourceConnection
import com.zaxxer.hikari.HikariDataSource
import org.springframework.stereotype.Component
import java.sql.Connection

@Component("hikari")
class HikariPoolDatasourceConnection : DataSourceConnection {
    private val ds: HikariDataSource = HikariDataSource() // -> DataSource 구현체

    init {
        ds.jdbcUrl = "jdbc:h2:tcp://localhost/~/db_practice"
        ds.username = "sa"
        ds.password = ""
        ds.maximumPoolSize = 10
        ds.poolName = "[Hikari Connection Pool]"
    }

    override fun getConnection(): Connection = ds.connection
}