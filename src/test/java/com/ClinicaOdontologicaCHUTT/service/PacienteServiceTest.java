package com.ClinicaOdontologicaCHUTT.service;

import com.ClinicaOdontologicaCHUTT.entity.Domicilio;
import com.ClinicaOdontologicaCHUTT.entity.Paciente;
import com.ClinicaOdontologicaCHUTT.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PacienteServiceTest {

    //ESTOS TESTS ESTÁN HECHOS PARA EJECUTARSE TODOS A LA VEZ, UNO DETRAS DE OTRO.
    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    void guardarPaciente() {
        Assertions.assertNotNull(pacienteService.guardarPaciente(new Paciente("Mateo","Chutt","ABC123", LocalDate.now(), new Domicilio("Av. Siempreviva",1234,"Springfield","Texas"),"correo@gmail.com")));
    }

    @Test
    @Order(2)
    void buscarPorEmail() {
        Assertions.assertTrue(pacienteService.buscarPorEmail("correo@gmail.com").isPresent());
    }

    @Test
    @Order(3)
    void buscarPacientePorId() {
        Assertions.assertTrue(pacienteService.buscarPacientePorId(1L).isPresent());
    }

    @Test
    @Order(4)
    void actualizarPaciente() {
        Paciente paciente = new Paciente(1L,"ACTUALIZADO","ACTUALIZADO","ACTUALIZADO", LocalDate.now(), new Domicilio("Av. ACTUALIZADO",1234,"ACTUALIZADO","ACTUALIZADO"),"ACTUALIZADO");
        assertEquals(pacienteService.actualizarPaciente(paciente).toString(),pacienteService.buscarPacientePorId(1L).get().toString());
    }

    @Test
    @Order(5)
    void listarPacientes() {
        Assertions.assertEquals(1,pacienteService.listarPacientes().size());
    }

    @Test
    @Order(6)
    void eliminarPaciente() throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(1L);
        Assertions.assertFalse(pacienteService.buscarPacientePorId(1L).isPresent());
    }
}