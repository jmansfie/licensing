package com.method5.licensing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(
        entityManagerFactoryRef = "licenseEntityManagerFactory",
        transactionManagerRef = "licenseTransactionManager",
        basePackages = {"com.method5.licensing.dao"})
public class LicenseDBConfiguration {

    @Value("${jpa.properties.hibernate.dialect}")
    private String dialect;

    @Primary
    @Bean(destroyMethod="")
    @ConfigurationProperties(prefix="datasource")
    public DataSource dataSource() throws Exception {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "licenseEntityManager")
    public EntityManager entityManager() throws Exception{
        return entityManagerFactory().createEntityManager();
    }


    private HibernateJpaVendorAdapter vendorAdaptor() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    @Primary
    @Bean(name = "licenseEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaVendorAdapter(vendorAdaptor());
        emf.setPackagesToScan("com.method5.licensing.domain");
        emf.setPersistenceUnitName("licensePersistenceUnit");
        emf.setJpaProperties(properties);
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    @Bean(name = "licenseTransactionManager")
    public PlatformTransactionManager transactionManager() throws Exception{
        return new JpaTransactionManager(entityManagerFactory());
    }
}