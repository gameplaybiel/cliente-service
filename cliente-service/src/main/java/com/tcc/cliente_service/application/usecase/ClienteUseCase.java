package com.tcc.cliente_service.application.usecase;

import com.tcc.cliente_service.application.dto.ClienteDTO;
import com.tcc.cliente_service.domain.model.Cliente;
import com.tcc.cliente_service.domain.repository.ClienteRepository;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class ClienteUseCase {
    private final ClienteRepository clienteRepository;

    public ClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente criarCliente(ClienteDTO dto) {
        Cliente cliente = new Cliente(
                dto.getNome(),
                dto.getCpf(),
                dto.getEmail(),
                dto.getTelefone(),
                dto.getEndereco()
        );
        // Validação de CPF básica (pode ser extraída para ClienteService se quiser manter separado)
        if (cliente.getCpf() == null || !cliente.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido");
        }

        return clienteRepository.save(cliente);
    }

    public boolean existeCliente(Long id) {
        return clienteRepository.existsById(id); // Método herdado do JpaRepository
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));
    }

    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
