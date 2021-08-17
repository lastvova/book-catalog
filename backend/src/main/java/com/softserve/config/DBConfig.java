package com.softserve.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:hibernate.properties")
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
    public DataSource getDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(getHibernateProperties().getProperty("hibernate.connection.url"));
        dataSource.setUser(getHibernateProperties().getProperty("hibernate.connection.username"));
        dataSource.setPassword(getHibernateProperties().getProperty("hibernate.connection.password"));
        try {
            dataSource.setDriverClass(getHibernateProperties().getProperty("hibernate.connection.driver_class"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        return adapter;
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(getDataSource());
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setPersistenceUnitName("basicEntities");
        emf.setPackagesToScan("com.softserve.entity");
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

//    @Bean
//    public HikariDataSource getDataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(getHibernateProperties().getProperty("hibernate.connection.url"));
//        config.setUsername(getHibernateProperties().getProperty("hibernate.connection.username"));
//        config.setPassword(getHibernateProperties().getProperty("hibernate.connection.password"));
//        config.setMinimumIdle(NumberUtils.toInt((getHibernateProperties().getProperty("dataSource.minimumIdle")), 5));
//        config.setMaximumPoolSize(NumberUtils.toInt((getHibernateProperties().getProperty("dataSource.maximumPoolSize")), 20));
//        config.setIdleTimeout(NumberUtils.toInt((getHibernateProperties().getProperty("dataSource.idleTimeout")), 30000));
//        config.setConnectionTimeout(NumberUtils.toInt((getHibernateProperties().getProperty("dataSource.connectionTimeout")), 30000));
//        config.addDataSourceProperty("cachePrepStmts", getHibernateProperties().getProperty("dataSource.cachePrepStmts"));
//        config.addDataSourceProperty("prepStmtCacheSize", getHibernateProperties().getProperty("dataSource.prepStmtCacheSize"));
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", getHibernateProperties().getProperty("dataSource.prepStmtCacheSqlLimit"));
//        config.addDataSourceProperty("useServerPrepStmts", getHibernateProperties().getProperty("dataSource.useServerPrepStmts"));
//        config.addDataSourceProperty("useLocalSessionState", getHibernateProperties().getProperty("dataSource.useLocalSessionState"));
//        return new HikariDataSource(config);
//    }
//    @Bean
//    public LocalSessionFactoryBean getSessionFactory() {
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(getDataSource());
//        sessionFactoryBean.setHibernateProperties(getHibernateProperties());
//        sessionFactoryBean.setPackagesToScan("com.softserve");
//        return sessionFactoryBean;
//    }
//}
