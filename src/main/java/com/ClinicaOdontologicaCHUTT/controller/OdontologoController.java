package com.ClinicaOdontologicaCHUTT.controller;

import com.ClinicaOdontologicaCHUTT.entity.Odontologo;
import com.ClinicaOdontologicaCHUTT.exception.ResourceNotFoundException;
import com.ClinicaOdontologicaCHUTT.service.OdontologoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private final OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarOdontologo(@RequestBody Odontologo odontologo) {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(odontologo.getMatricula());
        ResponseEntity<?> response = null;
        if (odontologoBuscado.isPresent()) {
            response = ResponseEntity.badRequest().body("La matricula que intentas guardar ya tiene un odontologo asociado");
        } else {
            response = ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologos() {
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerOdontologo(@PathVariable Long id) {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
        ResponseEntity<?> response = null;
        if (odontologoBuscado.isPresent()) {
            response = ResponseEntity.ok(odontologoBuscado.get());
        } else {
            response = ResponseEntity.badRequest().body("Odontologo con id: " + id + " no encontrado");
        }
        return response;
    }

    @GetMapping("/buscar/{matricula}")
    public ResponseEntity<?> obtenerOdontologoPorEmail(@PathVariable String matricula) {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(matricula);
        ResponseEntity<?> response = null;
        if (odontologoBuscado.isPresent()) {
            response = ResponseEntity.ok(odontologoBuscado.get());
        } else {
            response = ResponseEntity.badRequest().body("Odontologo con email: " + matricula + " no encontrado");
        }
        return response;
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarOdontologo(@RequestBody Odontologo odontologo) {
        Optional<?> odontologoBuscado = odontologoService.buscarOdontologoPorId(odontologo.getId());
        ResponseEntity<?> response = null;
        if (odontologoBuscado.isPresent()) {
            response = ResponseEntity.ok(odontologoService.actualizarOdontologo(odontologo));
        } else {
            response = ResponseEntity.badRequest().body("Imposible actualizar el usuario con id: " + odontologo.getId());
        }
        return response;
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("Odontologo eliminado");
    }
}
