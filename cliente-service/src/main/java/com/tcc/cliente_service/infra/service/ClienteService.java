package com.tcc.cliente_service.infra.service;

import com.tcc.cliente_service.application.dto.PedidoRequest;
import com.tcc.cliente_service.application.dto.PedidoResponse;
import com.tcc.cliente_service.domain.model.Cliente;
import com.tcc.cliente_service.domain.repository.ClienteRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final RestTemplate restTemplate;
    private final String pedidosServiceUrl;

    public ClienteService(ClienteRepository clienteRepository, RestTemplate restTemplate, String pedidosServiceUrl) {
        this.clienteRepository = clienteRepository;
        this.restTemplate = restTemplate;
        this.pedidosServiceUrl = pedidosServiceUrl;
    }

    public void salvarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public List<Cliente> listarCliente(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarClientePorId(Long id){
        return clienteRepository.findById(id);
    }

    public void deletarCliente(Long id){
        clienteRepository.deleteById(id);
    }

    public PedidoResponse criarPedidoParaCliente(Long clienteId, PedidoRequest pedidoRequest) {
        // Verificar se o cliente existe
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Configurar a requisição
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PedidoRequest> requestEntity = new HttpEntity<>(pedidoRequest, headers);

        // Fazer a chamada REST
        ResponseEntity<PedidoResponse> response = restTemplate.exchange(
                pedidosServiceUrl,
                HttpMethod.POST,
                requestEntity,
                PedidoResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Falha ao criar pedido - Status: " + response.getStatusCode());
        }

        return response.getBody();
    }
}
