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

    //        try with environment
    private Properties getHibernateProperties() throws IOException {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        properties.load(classLoader.getResourceAsStream("hibernate.properties"));
        return properties;
    }

    @Bean
    public DataSource getDataSource() throws PropertyVetoException, IOException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        Properties hibernateProperties = getHibernateProperties();
        dataSource.setJdbcUrl(hibernateProperties.getProperty("hibernate.connection.url"));
        dataSource.setUser(hibernateProperties.getProperty("hibernate.connection.username"));
        dataSource.setPassword(hibernateProperties.getProperty("hibernate.connection.password"));
        dataSource.setDriverClass(hibernateProperties.getProperty("hibernate.connection.driver_class"));
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
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPersistenceUnitName("basicEntities");
        emf.setPackagesToScan("com.softserve.entity");
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}