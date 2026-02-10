package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeResponseDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.entity.Employee;
import br.com.tiago.schermack.projeto_teste_automatizado.repository.EmployeeRepository;

@WebMvcTest (EmployeeService.class)
public class EmployeeServiceTest {
  
    @InjectMocks
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeRepository employeeRepository;
    private EmployeeRequestDTO requestDTO;
    private EmployeeResponseDTO responseDTO;





    @Test
    void DeveCriarFuncionário() {

        //Arrange
       EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("João", "joao@email.com");
       Employee employee = new Employee(requestDTO.getFirstName(), requestDTO.getEmail());



        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        //Act
        



    }

    @Test
    void testDelete() {

    }

    @Test
    void testFindAll() {

    }

    @Test
    void testFindById() {

    }

    @Test
    void testUpdate() {

    }
}
