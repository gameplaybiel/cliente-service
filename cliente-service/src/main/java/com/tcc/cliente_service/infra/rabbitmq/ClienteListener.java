package com.tcc.cliente_service.infra.rabbitmq;

import com.tcc.cliente_service.domain.model.Cliente;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClienteListener {
    private final RabbitTemplate rabbitTemplate;

    public ClienteListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${cliente.queue}")
    public void handleClienteCriado(Cliente cliente) {
        // Lógica para processar cliente criado
        System.out.println("Cliente recebido: " + cliente.getNome());
    }
}
