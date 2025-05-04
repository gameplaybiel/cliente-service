package com.tcc.cliente_service.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponse(
        Long id,
        Long clienteId,
        String descricao,
        BigDecimal valor,
        LocalDateTime dataCriacao
) {}