package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeResponseDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.entity.Employee;
import br.com.tiago.schermack.projeto_teste_automatizado.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;



    @Test
    @DisplayName("Deve criar usuário e retornar com sucesso")
    public void deveCriarUsuarioERetornarComSucesso(){
        //Arrange
       EmployeeRequestDTO requestDTO = 
            new EmployeeRequestDTO("Gustavo", "gustavo@email.com");

        //Act
        EmployeeResponseDTO responseDTO =
            employeeService.create(requestDTO);

        //Assert
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.id());
        assertEquals("Gustavo", responseDTO.firstName());
        assertEquals("gustavo@email.com", responseDTO.email());
       
        //validação no banco
        Employee employeeSalvo =
            employeeRepository.findById(responseDTO.id()).orElse(null);
        
        assertNotNull(employeeSalvo);
        assertEquals("Gustavo", employeeSalvo.getFirstName());
        assertEquals("gustavo@email.com", employeeSalvo.getEmail());


    }
    @Test
    @DisplayName("Deve criar e deletar um funcionário com sucesso")
    public void deveCriarEDeletarFuncionarioComSucesso(){
        //Arrange
        Employee employee = new Employee("Gustavo", "gustavo@email.com");
        employee = employeeRepository.save(employee);

        Long id = employee.getId();

        //validar existência
        assertTrue(employeeRepository.existsById(id));

        //Act
        employeeService.delete(id);

        //Assert
        assertFalse(employeeRepository.existsById(id));

    }

    @Test
    @DisplayName("Deve atualizar funcionário com sucesso")
    public void deveAtualizarFuncionarioComSucesso(){

        //Arrange
        EmployeeRequestDTO update = 
            new EmployeeRequestDTO("Gustavo", "gustavo@email.com");
        EmployeeRequestDTO create = 
            new EmployeeRequestDTO("Gugu", "gugu@email.com");

        EmployeeResponseDTO responseCreate =  employeeService.create(create);

        //Act
        EmployeeResponseDTO responseDTO = employeeService.update(responseCreate.id(), update);

        //Arrange
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals("Gustavo", responseDTO.firstName());
        assertEquals("gustavo@email.com", responseDTO.email());

    }

    @Test
    @DisplayName("Deve atualizar funcionário inexistente e retornar erro")
    public void deveAtualizarFuncionarioInexistenteERetornarErro(){

        //Arrange
        Long id = 1L;
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Gustavo", "gustavo@email.com");

        //Act + Assert
        assertThrows(EntityNotFoundException.class, () -> {
            employeeService.update(id, requestDTO);
        });
    }

}
