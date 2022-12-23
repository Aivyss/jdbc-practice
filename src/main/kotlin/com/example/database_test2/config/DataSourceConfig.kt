package com.example.database_test2.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

class DataSourceProperty {
    val url = "jdbc:h2:tcp://localhost/~/db_practice"
    val username = "sa"
    val password = ""
}

@Configuration
class DataSourceConfig {
    val property: DataSourceProperty = DataSourceProperty()

    @Bean("driverManagerDS")
    fun driverManagerDataSource(): DataSource {
        return HikariDataSource().apply {
            jdbcUrl = property.url
            username = property.username
            password = property.password
            maximumPoolSize = 10
            poolName = "[Hikari Connection Pool]"
        }
    }

    /**
     * DriverManager.getConnection(url, username, password)
     * java.sql은 DataSource의 추상화에 위반하기 때문에 스프링에서 제공하는 것을 쓴다.
     */
    @Bean("hikariDS")
    fun hikariPoolDataSource(): DataSource {
        return DriverManagerDataSource().apply {
            url = property.url
            username = property.username
            password = property.password
        }
    }
}