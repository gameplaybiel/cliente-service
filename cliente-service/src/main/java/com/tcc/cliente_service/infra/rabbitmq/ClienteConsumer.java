package com.tcc.cliente_service.infra.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ClienteConsumer {

        @RabbitListener(queues = RabbitMQConfig.CLIENTE_CRIADO_QUEUE)
        public void receberClienteCriado(ClienteCriadoEvent event){
            System.out.println("Cliente recebido via mensageria" + event.getNome());
        }
}
