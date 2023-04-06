package com.ClinicaOdontologicaCHUTT.controller;

import com.ClinicaOdontologicaCHUTT.dto.TurnoDTO;
import com.ClinicaOdontologicaCHUTT.entity.Turno;
import com.ClinicaOdontologicaCHUTT.exception.BadRequestException;
import com.ClinicaOdontologicaCHUTT.exception.ResourceNotFoundException;
import com.ClinicaOdontologicaCHUTT.service.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    /*@PostMapping("/guardar")
    public Turno guardarTurno(@RequestBody TurnoDTO turnoDTO) throws BadRequestException {
        Optional<TurnoDTO> turnoDTOBuscado = turnoService.buscarTurno(turnoDTO.getId());
        if (turnoDTOBuscado.isPresent()){
            throw new BadRequestException("ya existe");
        }else {
            return turnoService.guardarTurno(turnoDTO);
        }
    }*/

    @PostMapping("/guardar")
    public ResponseEntity<TurnoDTO> guardarTurno(@RequestBody TurnoDTO turnoDTO) throws BadRequestException {
        if (estaCompleto(turnoDTO) && turnoService.buscarTurno(turnoDTO.getId()).isEmpty()){
            return ResponseEntity.ok(turnoService.guardarTurno(turnoDTO));
        }else {
            throw new BadRequestException("No está completo el turno o ya hay un turno con esa misma ID "+turnoDTO.getId());
        }
    }

    private boolean estaCompleto(TurnoDTO turnoDTO) {
        boolean retorno = false;
        if (turnoDTO.getId() != 0 && turnoDTO.getId() != null && turnoDTO.getFecha() != null && turnoDTO.getPacienteId() != null && turnoDTO.getPacienteId() != 0 && turnoDTO.getOdontologoId() != null && turnoDTO.getOdontologoId() != 0) {
            retorno = true;
        }
        return retorno;
    }

    @GetMapping
    public List<TurnoDTO> listarPacientes() {
        return turnoService.listarTurnos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTurno(@PathVariable Long id) {
        Optional<TurnoDTO> turnoDTOBuscado = turnoService.buscarTurno(id);
        ResponseEntity<?> response = null;
        if (turnoDTOBuscado.isPresent()) {
            response = ResponseEntity.ok(turnoDTOBuscado.get());
        } else {
            response = ResponseEntity.badRequest().body("Turno con id: " + id + " no encontrado");
        }
        return response;
    }


    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarPaciente(@RequestBody TurnoDTO turnoDTO) {
        Optional<?> turnoBuscado = turnoService.buscarTurno(turnoDTO.getId());
        ResponseEntity<?> response = null;
        if (turnoBuscado.isPresent()) {
            turnoService.actualizarTurnoDTO(turnoDTO);
            response = ResponseEntity.ok("Turno actualizado correctamente");
        } else {
            response = ResponseEntity.badRequest().body("Imposible actualizar el turno con id: " + turnoDTO.getId());
        }
        return response;
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Turno eliminado");
    }

}