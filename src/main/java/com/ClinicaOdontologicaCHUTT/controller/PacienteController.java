package com.ClinicaOdontologicaCHUTT.controller;

import com.ClinicaOdontologicaCHUTT.entity.Paciente;
import com.ClinicaOdontologicaCHUTT.exception.ResourceNotFoundException;
import com.ClinicaOdontologicaCHUTT.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarPaciente(@RequestBody Paciente paciente) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorEmail(paciente.getEmail());
        ResponseEntity<?> response = null;
        if (pacienteBuscado.isPresent()) {
            response = ResponseEntity.badRequest().body("El correo que intentas guardar ya tiene un usuario asociado");
        } else {
            response = ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
        }
        return response;
    }

    @GetMapping
    public List<Paciente> listarPacientes() {
        return pacienteService.listarPacientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPaciente(@PathVariable Long id) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(id);
        ResponseEntity<?> response = null;
        if (pacienteBuscado.isPresent()) {
            response = ResponseEntity.ok(pacienteBuscado.get());
        } else {
            response = ResponseEntity.badRequest().body("Usuario con id: " + id + " no encontrado");
        }
        return response;
    }

    @GetMapping("/buscar/{email}")
    public ResponseEntity<?> obtenerPacientePorEmail(@PathVariable String email) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorEmail(email);
        ResponseEntity<?> response = null;
        if (pacienteBuscado.isPresent()) {
            response = ResponseEntity.ok(pacienteBuscado.get());
        } else {
            response = ResponseEntity.badRequest().body("Usuario con email: " + email + " no encontrado");
        }
        return response;
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarPaciente(@RequestBody Paciente paciente) {
        Optional<?> pacienteBuscado = pacienteService.buscarPacientePorId(paciente.getId());
        ResponseEntity<?> response = null;
        if (pacienteBuscado.isPresent()) {
            response = ResponseEntity.ok(pacienteService.actualizarPaciente(paciente));
        } else {
            response = ResponseEntity.badRequest().body("Imposible actualizar el usuario con id: " + paciente.getId());
        }
        return response;
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("Paciente eliminado");
    }

}
