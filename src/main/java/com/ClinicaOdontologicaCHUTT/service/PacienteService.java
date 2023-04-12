package com.ClinicaOdontologicaCHUTT.service;

import com.ClinicaOdontologicaCHUTT.entity.Paciente;
import com.ClinicaOdontologicaCHUTT.exception.BadRequestException;
import com.ClinicaOdontologicaCHUTT.exception.ResourceNotFoundException;
import com.ClinicaOdontologicaCHUTT.repository.IPacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private static final Logger LOGGER = Logger.getLogger(PacienteService.class);
    private final IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        if (buscarPacientePorId(id).isPresent()) {
            pacienteRepository.deleteById(id);
        } else {
            LOGGER.warn("No se puede eliminar ese paciente porque no existe en la db");
            throw new ResourceNotFoundException("No se puede eliminar ese paciente porque no existe en la db");
        }
    }

    public Optional<Paciente> buscarPacientePorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Optional<Paciente> buscarPorEmail(String email) {
        return pacienteRepository.findByEmail(email);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

}
