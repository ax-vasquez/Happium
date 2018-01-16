package io.happium.supported_capabilities_client_service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * This configuration class contains all beans which may be used
 * for interacting with a local database DataSource
 */
@Configuration
@Transactional
public class DatabaseConfiguration {

    /**
     * Utility method to quickly-construct new LocalContainerEntityManagerFactoryBeans
     *
     * <p>
     *     The Factory Beans created by this method are configured to use a Data source
     *     as defined in the dataSource() method. This will eventually need to be modified
     *     to create a database if one by the specified name does not exist (this can be done
     *     in the dataSource() method by adding a special query to the end of the setUrl() string.
     * <p>
     *     A new JPA adapter is created and then configured to use Hibernate ORM. That JPA
     *     adapter is then used as the LocalContainerEntityManagerFactoryBean's JPA vendor
     *     adapter. Some additional properties are configured before the method returns.
     *
     * @return              New LocalContainerEntityManagerFactoryBean to interact with specified data source
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());

        // Location of application entities and Repositories - should be in persistence package
        em.setPackagesToScan( "io.happium.supported_capabilities_client_service.persistence" );

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties( _additionalProperties() );
        return em;
    }

    /**
     * DataSource configuration bean
     *
     * <p>
     *     Configures JPA datasource using the
     *     PostgreSQL driver and points to the
     *     happium database (Happium database
     *     must be created in PostgreSQL prior
     *     to use).
     * <p>
     *     Thanks to how Spring works, this bean
     *     will be reused anywhere a DataSource
     *     is required (as is the case with DI
     *     in general)
     *
     * @return          Configured datasource (or reference to connected datasource)
     */
    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/happium");
        dataSource.setUsername( "happium" );
        dataSource.setPassword( "happium" );
        return dataSource;

    }

    /**
     * PlatformTransactionManager is the central interface in Spring's
     * transaction infrastructure.
     *
     * <p>
     *     This is what drives the interaction between Spring application
     *     and the database.
     *
     * @param emf
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf ){

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( emf );

        return transactionManager;
    }

    /**
     * Required to automatically apply persistence exception translation to
     * any bean marked as a Repository.
     *
     * @return
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Additional settings to configure the JPA
     * properties with
     *
     * @return
     */
    private Properties _additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }

}
