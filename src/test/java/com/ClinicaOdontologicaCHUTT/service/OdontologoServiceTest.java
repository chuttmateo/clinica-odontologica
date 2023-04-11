package com.ClinicaOdontologicaCHUTT.service;

import com.ClinicaOdontologicaCHUTT.entity.Odontologo;
import com.ClinicaOdontologicaCHUTT.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class OdontologoServiceTest {

    //ESTOS TESTS ESTÁN HECHOS PARA EJECUTARSE TODOS A LA VEZ, UNO DETRAS DE OTRO.

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void guardarOdontologo() {
        odontologoService.guardarOdontologo(new Odontologo("ABC123","Mateo","Chutt"));
        Assertions.assertNotNull(odontologoService.buscarOdontologoPorId(1L).get());
    }

    @Test
    @Order(2)
    void buscarPorMatricula() {
        Assertions.assertTrue(odontologoService.buscarPorMatricula("ABC123").isPresent());
    }

    @Test
    @Order(3)
    void buscarOdontologoPorId() {
        Assertions.assertTrue(odontologoService.buscarOdontologoPorId(1L).isPresent());
    }

    @Test
    @Order(4)
    void actualizarOdontologo() {
        Odontologo odo = new Odontologo(1L,"ACTUALIZADO","ACTUALIZADO","ACTUALIZADO");
        assertEquals(odontologoService.actualizarOdontologo(odo).toString(),odontologoService.buscarOdontologoPorId(1L).get().toString());
    }

    @Test
    @Order(5)
    void listarOdontologos() {
        Assertions.assertEquals(1,odontologoService.listarOdontologos().size());
    }

    @Test
    @Order(6)
    void eliminarOdontologo() throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(1L);
        Assertions.assertFalse(odontologoService.buscarOdontologoPorId(1L).isPresent());
    }
}