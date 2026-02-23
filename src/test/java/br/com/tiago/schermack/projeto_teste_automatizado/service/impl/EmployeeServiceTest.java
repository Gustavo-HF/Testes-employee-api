package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeResponseDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.entity.Employee;
import br.com.tiago.schermack.projeto_teste_automatizado.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Esse teste vai ser responsável por validar a criação do funcionário")
    public void deveCriarEmpregadoERetornarResponseDTO(){

        //Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Gustavo", "gustavo@email.com");
        Employee employeeSaved = new Employee(requestDTO.firstName(), requestDTO.email());
        employeeSaved.setId(1L);

        when(employeeRepository.save(any(Employee.class)))
            .thenReturn(employeeSaved);

        //Act
        EmployeeResponseDTO responseDTO = employeeService.create(requestDTO);

        //Assert
        assertEquals(1L, responseDTO.id());
        assertEquals("Gustavo", responseDTO.firstName());
        assertEquals("gustavo@email.com", responseDTO.email());

        verify(employeeRepository, times(1)).save(any(Employee.class));

        
    }

    @Test
    @DisplayName("Esse teste vai ser responsável por atualizar as informações inseridas no sistema")
    public void deveAtualizarEmpregadoERetornarResponseDTOComSucesso(){

        //Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Gugu", "gugu@email.com");
        Employee employee = new Employee("Gustavo", "gustavo@email.com");
        employee.setId(1L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        //Act
        EmployeeResponseDTO responseDTO = employeeService.update(1L, requestDTO);
        
        //Assert
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals("Gugu", responseDTO.firstName());
        assertEquals("gugu@email.com", responseDTO.email());

        verify(employeeRepository, times(1)).save(any(Employee.class));


    }

    @Test
    @DisplayName("Deve retornar erro ao atualizar usuário não existente")
    public void deveDarErroQuandoAtualizarFuncionarioQueNaoExistir(){

        //Arrange
        Long id = 1L;
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Gugu", "gugu@email.com");

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(EntityNotFoundException.class, () -> {
            employeeService.update(id, requestDTO);
        });

        // Verifica se save não foi chamado
        verify(employeeRepository, never()).save(any(Employee.class));


    }

    @Test
    public void deveEcontrarUmFuncionarioComSucesso(){
        //Arrange
        Long id = 1L;
        Employee employee = new Employee("Gustavo", "gustavo@gmail.com");
        employee.setId(id);

        when(employeeRepository.findById(id))
            .thenReturn(Optional.of(employee));

        //Act
        EmployeeResponseDTO responseDTO = employeeService.findById(id);

        //Assert
        assertNotNull(responseDTO);
        assertEquals(id, responseDTO.id());
        assertEquals("Gustavo", responseDTO.firstName());
        assertEquals("gustavo@gmail.com", responseDTO.email());

        verify(employeeRepository, times(1)).findById(id);
    }
}