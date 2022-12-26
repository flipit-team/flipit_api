package com.flip.data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"com.flip.data"})
@EntityScan(basePackages = {"com.flip.data.entity"})
@EnableJpaRepositories(basePackages = {"com.flip.data.repository"})
public class DataConfig {

    @Autowired
    Environment env;

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
