package com.tcc.cliente_service.domain.repository;

import com.tcc.cliente_service.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsById(Long id);
}
