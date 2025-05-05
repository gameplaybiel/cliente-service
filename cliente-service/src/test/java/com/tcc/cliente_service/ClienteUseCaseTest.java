package com.tcc.cliente_service;

import com.tcc.cliente_service.application.dto.ClienteDTO;
import com.tcc.cliente_service.application.usecase.ClienteUseCase;
import com.tcc.cliente_service.domain.model.Cliente;
import com.tcc.cliente_service.domain.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith( MockitoExtension.class)
public class ClienteUseCaseTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteUseCase clienteUseCase;

    @Test
    void criarCliente_ComDadosValidos_DeveRetornarClienteSalvo() {
        // Arrange
        ClienteDTO dto = new ClienteDTO(
                "João",
                "12345678901",  // SEM formatação
                "joao@email.com",
                "11999998888",
                "Rua Teste, 123");

        Cliente clienteSalvo = new Cliente(
                dto.getNome(),
                dto.getCpf(),
                dto.getEmail(),
                dto.getTelefone(),
                dto.getEndereco());

        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);

        // Act
        Cliente resultado = clienteUseCase.criarCliente(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void criarCliente_ComCPFInvalido_DeveLancarExcecao() {
        // Arrange
        ClienteDTO dto = new ClienteDTO(
                "João",
                "1234567890",  // CPF inválido
                "joao@email.com",
                "(11)98441-9999",
                "Avenida José Marion");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clienteUseCase.criarCliente(dto));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void existeCliente_QuandoClienteExiste_DeveRetornarTrue() {
        // Arrange
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);

        // Act
        boolean resultado = clienteUseCase.existeCliente(id);

        // Assert
        assertTrue(resultado);
        verify(clienteRepository, times(1)).existsById(id);
    }

    @Test
    void listarClientes_QuandoExistemClientes_DeveRetornarLista() {
        // Arrange
        Cliente cliente = new Cliente("João", "460.650.258-79", "joao@email.com", "(11)98441-9999", "Avenida José Marion");
        when(clienteRepository.findAll()).thenReturn(Collections.singletonList(cliente));

        // Act
        List<Cliente> resultado = clienteUseCase.listarClientes();

        // Assert
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void buscarClientePorId_QuandoClienteExiste_DeveRetornarCliente() {
        // Arrange
        Long id = null;
        Cliente cliente = new Cliente("João", "12345678901", "joao@email.com", "11999998888", "Rua Teste");
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        // Act
        Cliente resultado = clienteUseCase.buscarClientePorId(id);

        // Assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    void buscarClientePorId_QuandoClienteNaoExiste_DeveLancarExcecao() {
        // Arrange
        Long id = 99L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> clienteUseCase.buscarClientePorId(id));
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    void deletarCliente_DeveChamarRepositorio() {
        // Arrange
        Long id = null;
        doNothing().when(clienteRepository).deleteById(id);

        // Act
        clienteUseCase.deletarCliente(id);

        // Assert
        verify(clienteRepository, times(1)).deleteById(id);
    }
}
