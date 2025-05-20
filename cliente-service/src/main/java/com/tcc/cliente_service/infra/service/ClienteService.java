package com.tcc.cliente_service.infra.service;

import com.tcc.cliente_service.application.dto.PedidoRequest;
import com.tcc.cliente_service.application.dto.PedidoResponse;
import com.tcc.cliente_service.domain.model.Cliente;
import com.tcc.cliente_service.domain.repository.ClienteRepository;
import com.tcc.cliente_service.infra.config.ServiceProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final RestTemplate restTemplate;
    private final ServiceProperties properties;

    public ClienteService(ClienteRepository clienteRepository, RestTemplate restTemplate, ServiceProperties properties) {
        this.clienteRepository = clienteRepository;
        this.restTemplate = restTemplate;
        this.properties = properties;
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
        // 1. Verificar se cliente existe
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // 2. Configurar headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3. Criar entidade da requisição
        HttpEntity<PedidoRequest> requestEntity = new HttpEntity<>(pedidoRequest, headers);

        // 4. Obter URL do ServiceProperties
        String url = properties.getPedidosUrl();

        // 5. Fazer a chamada (usando a URL string, não o objeto ServicoProperties)
        ResponseEntity<PedidoResponse> response = restTemplate.exchange(
                url,  // String com a URL
                HttpMethod.POST,
                requestEntity,
                PedidoResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Falha ao criar pedido");
        }

        return response.getBody();
    }
}
