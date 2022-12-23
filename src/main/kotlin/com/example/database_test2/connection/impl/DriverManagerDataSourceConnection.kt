package com.example.database_test2.connection.impl

import com.example.database_test2.connection.DataSourceConnection
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.sql.Connection
import javax.sql.DataSource

@Component("driverManager")
class DriverManagerDataSourceConnection(
    @Qualifier("driverManagerDS") private val ds: DataSource
) : DataSourceConnection {
    override fun getConnection(): Connection = ds.connection
}