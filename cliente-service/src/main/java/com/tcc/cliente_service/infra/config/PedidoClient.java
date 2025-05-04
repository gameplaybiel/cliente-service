package com.tcc.cliente_service.infra.config;

import com.tcc.cliente_service.application.dto.PedidoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "pedido-service",
        url = "${pedido.service.url}",
        configuration = FeignConfig.class
)
public interface PedidoClient {

    @GetMapping("/pedido/cliente/{clienteId}")
    List<PedidoResponse> listarPedidosPorCliente(@PathVariable Long clienteId);
}