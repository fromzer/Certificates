package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:database.properties")
public class DataConfiguration {
    @Value("${spring.datasource.driverName}")
    private String driverName;
    @Value("${spring.datasource.jdbcUrl}")
    private String jdbcUrl;
    @Value("${spring.datasource.login}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.maxPoolSize}")
    private String maxPoolSize;

    @Bean
    public DataSource dataSource() {
        HikariConfig jdbcConfig = new HikariConfig();
        jdbcConfig.setDriverClassName(driverName);
        jdbcConfig.setJdbcUrl(jdbcUrl);
        jdbcConfig.setUsername(username);
        jdbcConfig.setPassword(password);
        jdbcConfig.setMaximumPoolSize(Integer.parseInt(maxPoolSize));
        return new HikariDataSource(jdbcConfig);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

}
