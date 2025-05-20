package com.tcc.cliente_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PedidoRequest(
        @NotNull Long clienteId,
        @NotBlank String descricao,
        @Positive BigDecimal valor
        ) {
}
