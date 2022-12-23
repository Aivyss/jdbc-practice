package com.example.database_test2.connection.impl

import com.example.database_test2.connection.DataSourceConnection
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.sql.Connection
import javax.sql.DataSource

@Component("hikariDC")
class HikariPoolDatasourceConnection(
    @Qualifier("hikariDS") private val ds: DataSource
) : DataSourceConnection {
    override fun getConnection(): Connection = ds.connection
}