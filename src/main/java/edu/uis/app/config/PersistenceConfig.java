package edu.uis.app.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Pablo Delgado
 */
@Configuration
@EnableJpaRepositories(basePackages = {
        "edu.uis.app.data"
})
@EnableTransactionManagement
public class PersistenceConfig {
    
    DataSource dataSourceFromString(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
            return (DataSource) Class.forName(className).newInstance();
    }
    
    DataSource postgresDataSourceConfig(Environment env, PGPoolingDataSource dataSource) {
        dataSource.setServerName(env.getRequiredProperty("spring.datasource.dbhost"));
        dataSource.setPortNumber(Integer.parseInt(env.getRequiredProperty("spring.datasource.dbport")));
        dataSource.setDatabaseName(env.getRequiredProperty("spring.datasource.dbname"));
        dataSource.setUser(env.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
        dataSource.setInitialConnections(Integer.parseInt(env.getRequiredProperty("postgres.connection.pool.ini")));
        dataSource.setMaxConnections(Integer.parseInt(env.getRequiredProperty("postgres.connection.pool.max")));
        return dataSource;
    }
 
    @Bean(destroyMethod = "close")
    DataSource dataSource(Environment env) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DataSource dataSource = dataSourceFromString(env.getRequiredProperty("spring.datasource.driver-class-name"));
        if(dataSource instanceof PGPoolingDataSource)
            return postgresDataSourceConfig(env, (PGPoolingDataSource) dataSource);
        
        // TODO: Create configuration option for MySQL        
        
        throw new ClassNotFoundException("Datasource driver class not found");
    }
    
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, 
                                                                Environment env) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("edu.uis.app.data");
 
        Properties jpaProperties = new Properties();
     
        //Configures the used database dialect. This allows Hibernate to create SQL
        //that is optimized for the used database.
        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
 
        //Specifies the action that is invoked to the database when the Hibernate
        //SessionFactory is created or closed.
        jpaProperties.put("hibernate.hbm2ddl.auto", 
                env.getRequiredProperty("hibernate.hbm2ddl.auto")
        );
 
        //Configures the naming strategy that is used when Hibernate creates
        //new database objects and schema elements
        jpaProperties.put("hibernate.implicit_naming_strategy", 
                env.getRequiredProperty("hibernate.naming_strategy")
        );
 
        //If the value of this property is true, Hibernate writes all SQL
        //statements to the console.
        jpaProperties.put("hibernate.show_sql", 
                env.getRequiredProperty("hibernate.show_sql")
        );
 
        //If the value of this property is true, Hibernate will format the SQL
        //that is written to the console.
        jpaProperties.put("hibernate.format_sql", 
                env.getRequiredProperty("hibernate.format_sql")
        );
        
        // Enable reflection optimizer
        jpaProperties.put("hibernate.cglib.use_reflection_optimizer", 
                env.getRequiredProperty("hibernate.cglib.use_reflection_optimizer")
        );
         
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
 
        return entityManagerFactoryBean;
    }
    
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
     
}