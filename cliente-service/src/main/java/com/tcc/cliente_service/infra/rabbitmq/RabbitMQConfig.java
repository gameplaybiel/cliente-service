package com.tcc.cliente_service.infra.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${cliente.queue.name}")
    private String clienteQueueName;

    @Bean
    Queue clienteQueue() {
        return new Queue(clienteQueueName, true);
    }
}