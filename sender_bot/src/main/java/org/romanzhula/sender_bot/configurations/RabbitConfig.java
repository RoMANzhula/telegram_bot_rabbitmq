package org.romanzhula.sender_bot.configurations;


import lombok.extern.log4j.Log4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

import static org.romanzhula.rabbitmq_buffer.models.RabbitmqQueue.*;

@Log4j
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.queue.name.text}")
    private String queueNameText;

    @Value("${rabbitmq.queue.name.document}")
    private String queueNameDocument;

    @Value("${rabbitmq.queue.name.photo}")
    private String queueNamePhoto;

    @Value("${rabbitmq.queue.name.answer}")
    private String queueNameAnswer;


    @Bean
    public Queue textMessageQueue() {
        return new Queue(queueNameText);
    }

    @Bean
    public Queue photoMessageQueue() {
        return new Queue(queueNamePhoto);
    }

    @Bean
    public Queue documentMessageQueue() {
        return new Queue(queueNameDocument);
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue(queueNameAnswer);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        // For running rabbitmq during run our application
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);

        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(cachingConnectionFactory());
    }

    @Bean
    public ErrorHandler rabbitErrorHandler() {
        return (Throwable t) -> {
            log.error("Error handling message", t);

            throw new AmqpRejectAndDontRequeueException("Error processing message", t);
        };
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            CachingConnectionFactory cachingConnectionFactory,
            ErrorHandler rabbitErrorHandler
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setErrorHandler(rabbitErrorHandler);
        return factory;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getQueueNameText() {
        return queueNameText;
    }

    public String getQueueNameDocument() {
        return queueNameDocument;
    }

    public String getQueueNamePhoto() {
        return queueNamePhoto;
    }

    public String getQueueNameAnswer() {
        return queueNameAnswer;
    }

}
