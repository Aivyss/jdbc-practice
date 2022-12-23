package com.example.database_test2.connection

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DatabaseConnectionTest {
    @Test
    fun `db connection test`() {
        // * when
        val connection = DatabaseConnection.getConnectionWithDriverManager()

        // * then
        Assertions.assertThat(connection).isNotNull
    }

    @Test
    fun `datasource test - DriverManagerDataSource`() {
        // * when
        val connection = DatabaseConnection.getConnectionWithDriverManagerDataSource()

        // * then
        Assertions.assertThat(connection).isNotNull
    }

    @Test
    fun `datasource test - Hikari connection pool`() {
        // * when
        val connection = DatabaseConnection.getConnectionWithHikariConnectionPool()

        // * then
        Assertions.assertThat(connection).isNotNull

    }
}