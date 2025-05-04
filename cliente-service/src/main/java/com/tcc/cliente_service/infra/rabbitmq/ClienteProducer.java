package com.tcc.cliente_service.infra.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClienteProducer {
    private final RabbitTemplate rabbitTemplate;

    public ClienteProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarCLienteCriado(ClienteCriadoEvent event){
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CLIENTE_EXCHANGE,
                RabbitMQConfig.CLIENTE_ROUTING_KEY,
                event
        );
    }
}
