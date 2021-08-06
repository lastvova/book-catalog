package com.softserve.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement
public class DBConfig {

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        try with environment
        try {
            properties.load(classLoader.getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Bean
    public HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(getHibernateProperties().getProperty("hibernate.connection.url"));
        config.setUsername(getHibernateProperties().getProperty("hibernate.connection.username"));
        config.setPassword(getHibernateProperties().getProperty("hibernate.connection.password"));
        config.setMinimumIdle(NumberUtils.toInt((getHibernateProperties().getProperty("dataSource.minimumIdle")), 5));
        config.setMaximumPoolSize(NumberUtils.toInt((getHibernateProperties().getProperty("dataSource.maximumPoolSize")), 20));
        config.setIdleTimeout(NumberUtils.toInt((getHibernateProperties().getProperty("dataSource.idleTimeout")), 30000));
        config.setConnectionTimeout(NumberUtils.toInt((getHibernateProperties().getProperty("dataSource.connectionTimeout")), 30000));
        config.addDataSourceProperty("cachePrepStmts", getHibernateProperties().getProperty("dataSource.cachePrepStmts"));
        config.addDataSourceProperty("prepStmtCacheSize", getHibernateProperties().getProperty("dataSource.prepStmtCacheSize"));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", getHibernateProperties().getProperty("dataSource.prepStmtCacheSqlLimit"));
        config.addDataSourceProperty("useServerPrepStmts", getHibernateProperties().getProperty("dataSource.useServerPrepStmts"));
        config.addDataSourceProperty("useLocalSessionState", getHibernateProperties().getProperty("dataSource.useLocalSessionState"));

        return new HikariDataSource(config);
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDataSource());
        sessionFactoryBean.setHibernateProperties(getHibernateProperties());
        sessionFactoryBean.setPackagesToScan("com.softserve");
        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}
