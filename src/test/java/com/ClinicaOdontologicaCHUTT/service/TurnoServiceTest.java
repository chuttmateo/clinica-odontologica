package com.ClinicaOdontologicaCHUTT.service;

import com.ClinicaOdontologicaCHUTT.dto.TurnoDTO;
import com.ClinicaOdontologicaCHUTT.entity.Domicilio;
import com.ClinicaOdontologicaCHUTT.entity.Odontologo;
import com.ClinicaOdontologicaCHUTT.entity.Paciente;
import com.ClinicaOdontologicaCHUTT.entity.Turno;
import com.ClinicaOdontologicaCHUTT.exception.BadRequestException;
import com.ClinicaOdontologicaCHUTT.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class TurnoServiceTest {

    //ESTOS TESTS ESTÁN HECHOS PARA EJECUTARSE TODOS A LA VEZ, UNO DETRAS DE OTRO.

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private TurnoService turnoService;

    @Test
    @Order(1)
    void guardarTurno() throws BadRequestException {
        Paciente paciente = pacienteService.guardarPaciente(new Paciente("Mateo", "Chutt", "ABC123", LocalDate.now(), new Domicilio("Av. Siempreviva", 1234, "Springfield", "Texas"), "correo@gmail.com"));
        Odontologo odontologo = odontologoService.guardarOdontologo(new Odontologo("ABC123", "DR.Mateo", "DR.Chutt"));
        TurnoDTO turnoDTO = turnoService.guardarTurno(turnoATurnoDTO(new Turno(1L,paciente,odontologo,LocalDate.now())));
        Assertions.assertNotNull(turnoDTO);
    }

    @Test
    @Order(2)
    void listarTurnos() {
        Assertions.assertEquals(1, turnoService.listarTurnos().size());
    }

    @Test
    @Order(3)
    void buscarTurno() {
        Assertions.assertTrue(turnoService.buscarTurno(1L).isPresent());
    }

    @Test
    @Order(4)
    void actualizarTurnoDTO() {
        Paciente paciente = pacienteService.guardarPaciente(new Paciente("ACTUALIZADO", "ACTUALIZADO", "ACTUALIZADO", LocalDate.now(), new Domicilio("Av. ACTUALIZADO", 1234, "ACTUALIZADO", "ACTUALIZADO"), "ACTUALIZADO@ACTUALIZADO.ACTUALIZADO"));
        Odontologo odontologo = odontologoService.guardarOdontologo(new Odontologo("ACTUALIZADO", "DR.ACTUALIZADO", "DR.ACTUALIZADO"));
        turnoService.actualizarTurnoDTO(turnoATurnoDTO(new Turno(1L,paciente,odontologo,LocalDate.now())));
        Assertions.assertNotNull(turnoService.buscarTurno(1L));
    }

    @Test
    @Order(5)
    void eliminarTurno() throws ResourceNotFoundException {
        turnoService.eliminarTurno(1L);
        Assertions.assertFalse(turnoService.buscarTurno(1L).isPresent());
    }

    private TurnoDTO turnoATurnoDTO(Turno turno) {
        TurnoDTO respuesta = new TurnoDTO();
        respuesta.setId(turno.getId());
        respuesta.setFecha(turno.getFecha());
        respuesta.setOdontologoId(turno.getOdontologo().getId());
        respuesta.setPacienteId(turno.getPaciente().getId());
        return respuesta;
    }
}