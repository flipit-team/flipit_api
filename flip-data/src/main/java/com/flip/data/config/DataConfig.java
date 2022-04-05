package com.flip.data.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages ={"com.flip.data"})
@EntityScan(basePackages = {"com.flip.data"})
@ComponentScan(basePackages = {"com.flip.data"})
public class DataConfig {

    @Autowired
    Environment env;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName("flipiPU");
        em.setPackagesToScan(new String[]{"com.flip.data.entity"});
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        ((HibernateJpaVendorAdapter) vendorAdapter).setDatabasePlatform(env.getProperty("hibernate.dialect"));
        em.setJpaVendorAdapter(vendorAdapter);
        em.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        Map<String,Object> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put("javax.persistence.validation.mode", env.getProperty("javax.persistence.validation.mode"));
        jpaPropertyMap.put("hibernate.show_sql", env.getProperty("hibernate.show.sql"));
        jpaPropertyMap.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        jpaPropertyMap.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        jpaPropertyMap.put("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
        jpaPropertyMap.put("hibernate.hikari.dataSource.url", env.getProperty("flipi.jdbc.url"));
        jpaPropertyMap.put("hibernate.hikari.dataSource.user", env.getProperty("flipi.jdbc.username"));
        jpaPropertyMap.put("hibernate.hikari.dataSource.password", env.getProperty("flipi.jdbc.password"));
        jpaPropertyMap.put("hibernate.hikari.dataSourceClassName", env.getProperty("flipi.jdbc.datasource.class"));
        jpaPropertyMap.put("hibernate.connection.provider_class", env.getProperty("hibernate.provider_class"));
        jpaPropertyMap.put("hibernate.hikari.maxLifetime", env.getProperty("flipi.jdbc.maxLifetime"));
        jpaPropertyMap.put("hibernate.hikari.idleTimeout", env.getProperty("flipi.jdbc.maxidletime"));
        jpaPropertyMap.put("hibernate.hikari.connectionTimeout", env.getProperty("flipi.jdbc.connectTimeout"));
        jpaPropertyMap.put("hibernate.hikari.maximumPoolSize", env.getProperty("flipi.jdbc.maxPoolSize"));
        jpaPropertyMap.put("hibernate.hikari.minimumIdle", env.getProperty("flipi.jdbc.minPoolSize"));

        em.setJpaPropertyMap(jpaPropertyMap);
        em.setJpaDialect(new HibernateJpaDialect());
        em.setPersistenceUnitRootLocation("classpath:/com/flip/data/entity");
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        return em;
    }

    @Bean(name = "txManage")
    public PlatformTransactionManager transactionManager(@Autowired EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /*@Bean(name = "messageSource")
    public MessageSource createMessageResource(){
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        String basenName[] = new String[]{"file:"+ System.getenv("APP_SERVICE_CONFIG") +"/flipi/i18n/message"};
        reloadableResourceBundleMessageSource.setBasenames(basenName);
        reloadableResourceBundleMessageSource.setConcurrentRefresh(false);
        reloadableResourceBundleMessageSource.setCacheSeconds(-1);
        reloadableResourceBundleMessageSource.setFallbackToSystemLocale(false);
        return reloadableResourceBundleMessageSource;
    }*/

    /*public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(env.getProperty("jms.broker"));
        connectionFactory.setUserName(env.getProperty("jms.connectionfactory.username"));
        connectionFactory.setPassword(env.getProperty("jms.connectionfactory.password"));
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }*/

    /*@Bean(name = "jmsConnectionFactory")
    public PooledConnectionFactory jmsConnectionFactory() {
        String maxCon = env.getProperty("jms.maxconnection");
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setMaxConnections(Integer.parseInt(maxCon == null ? "8" : maxCon));
        pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory());
        return pooledConnectionFactory;
    }*/

    /*@Bean(name = "jmsTransactionManager")
    public JmsTransactionManager jmsTransactionManager(@Autowired ConnectionFactory connectionFactory) {
        JmsTransactionManager transactionManager = new JmsTransactionManager();
        transactionManager.setConnectionFactory(connectionFactory);
        return  transactionManager;
    }*/

    /*@Bean(name = "flipiEmailQueue")
    public ActiveMQQueue flipiEmailQueue(@Value("LigareEmailQueue") String queueName) {
        return new ActiveMQQueue(queueName);
    }

    @Bean(name = "flipiSMSQueue")
    public ActiveMQQueue flipiSMSQueue(@Value("LigareSMSQueue") String queueName) {
        return new ActiveMQQueue(queueName);
    }*/

    /*@Bean(name = "emailQueueJmsTemplate")
    public JmsTemplate emailQueueJmsTemplate(@Autowired ConnectionFactory jmsConnectionFactory,
                                             @Autowired @Qualifier("flipiEmailQueue") Destination destination) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(jmsConnectionFactory);
        jmsTemplate.setReceiveTimeout(1);
        jmsTemplate.setDefaultDestination(destination);
        return jmsTemplate;
    }*/

    /*@Bean(name = "smsQueueJmsTemplate")
    public JmsTemplate smsQueueJmsTemplate(@Autowired ConnectionFactory jmsConnectionFactory,
                                           @Autowired @Qualifier("flipiSMSQueue") Destination destination) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(jmsConnectionFactory);
        jmsTemplate.setReceiveTimeout(1);
        jmsTemplate.setDefaultDestination(destination);
        return jmsTemplate;
    }*/
}
