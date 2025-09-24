package com.juliomesquita.demoauditoria.infra.configs.database;

import com.juliomesquita.demoauditoria.infra.configs.database.properties.ClickHouseProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ClickHouseConfig {
    @Bean(name = "clickHouseDataSource")
    public DataSource clickHouseDataSource(ClickHouseProperties properties) {
        return DataSourceBuilder.create()
            .url(properties.getUrl())
            .username(properties.getUsername())
            .password(properties.getPassword())
            .driverClassName(properties.getDriverClassName())
            .type(com.zaxxer.hikari.HikariDataSource.class) // também usa Hikari
            .build();
    }

    @Bean(name = "clickHouseJdbcTemplate")
    public JdbcTemplate clickHouseJdbcTemplate(@Qualifier("clickHouseDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
