package com.tcc.cliente_service.infra.rabbitmq;

import com.tcc.cliente_service.domain.model.Cliente;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClienteEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public ClienteEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarClienteCriado(Cliente cliente){
        rabbitTemplate.convertAndSend("cliente.criado", cliente);
    }

    public void publicarClienteDeletado(Long clienteId) {
        rabbitTemplate.convertAndSend("cliente.deletado", clienteId);
    }
}
