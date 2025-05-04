package com.tcc.cliente_service.infra.controller;

import com.tcc.cliente_service.application.dto.ClienteDTO;
import com.tcc.cliente_service.application.usecase.ClienteUseCase;
import com.tcc.cliente_service.domain.model.Cliente;
import com.tcc.cliente_service.infra.rabbitmq.ClienteEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteUseCase clienteUseCase;
    private final ClienteEventPublisher clienteEventPublisher;

    public ClienteController(ClienteUseCase clienteUseCase, ClienteEventPublisher clienteEventPublisher) {
        this.clienteUseCase = clienteUseCase;
        this.clienteEventPublisher = clienteEventPublisher;
    }

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody ClienteDTO dto) {
        Cliente cliente = clienteUseCase.criarCliente(dto);
        clienteEventPublisher.publicarClienteCriado(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/{id}/existe")
    public ResponseEntity<Void> verificarExistencia(@PathVariable Long id) {
        return clienteUseCase.existeCliente(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteUseCase.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteUseCase.buscarClientePorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteUseCase.deletarCliente(id);
        clienteEventPublisher.publicarClienteDeletado(id);
        return ResponseEntity.noContent().build();
    }
}
