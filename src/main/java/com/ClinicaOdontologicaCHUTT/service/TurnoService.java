package com.ClinicaOdontologicaCHUTT.service;

import com.ClinicaOdontologicaCHUTT.dto.TurnoDTO;
import com.ClinicaOdontologicaCHUTT.entity.Odontologo;
import com.ClinicaOdontologicaCHUTT.entity.Paciente;
import com.ClinicaOdontologicaCHUTT.entity.Turno;
import com.ClinicaOdontologicaCHUTT.exception.BadRequestException;
import com.ClinicaOdontologicaCHUTT.exception.ResourceNotFoundException;
import com.ClinicaOdontologicaCHUTT.repository.IOdontologoRepository;
import com.ClinicaOdontologicaCHUTT.repository.IPacienteRepository;
import com.ClinicaOdontologicaCHUTT.repository.ITurnoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private final ITurnoRepository turnoRepository;
    private final IPacienteRepository pacienteRepository;
    private final IOdontologoRepository odontologoRepository;

    public TurnoService(ITurnoRepository turnoRepository, IPacienteRepository pacienteRepository, IOdontologoRepository odontologoRepository) {
        this.turnoRepository = turnoRepository;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
    }

    public List<TurnoDTO> listarTurnos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDTO> respuesta = new ArrayList<>();
        for (Turno t : turnos) {
            respuesta.add(turnoATurnoDTO(t));
        }
        return respuesta;
    }

    public Optional<TurnoDTO> buscarTurno(Long id) {
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        if (turnoBuscado.isPresent()) {
            return Optional.of(turnoATurnoDTO(turnoBuscado.get()));
        } else {
            return Optional.empty();
        }
    }

    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if (buscarTurno(id).isPresent()) {
            turnoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("No se puede eliminar este turno porque no existe en la db");
        }
    }

    public void actualizarTurnoDTO(TurnoDTO turnoDTO) {
        turnoRepository.save(turnoDTOATurno(turnoDTO));
    }

    public TurnoDTO guardarTurno(TurnoDTO turnoDTO) throws BadRequestException {
        if (pacienteRepository.findById(turnoDTO.getPacienteId()).isPresent() && odontologoRepository.findById(turnoDTO.getOdontologoId()).isPresent() && buscarTurno(turnoDTO.getId()).isEmpty()) {
            return turnoATurnoDTO(turnoRepository.save(turnoDTOATurno(turnoDTO)));
        } else {
            throw new BadRequestException("Un turno debe contener un ID de un odontologo y un ID de un paciente registrado anteriormente");
        }
    }

    private TurnoDTO turnoATurnoDTO(Turno turno) {
        TurnoDTO respuesta = new TurnoDTO();
        respuesta.setId(turno.getId());
        respuesta.setFecha(turno.getFecha());
        respuesta.setOdontologoId(turno.getOdontologo().getId());
        respuesta.setPacienteId(turno.getPaciente().getId());
        return respuesta;
    }

    private Turno turnoDTOATurno(TurnoDTO turnoDTO) {
        Turno respuesta = new Turno();
        Odontologo odontologo = new Odontologo();
        Paciente paciente = new Paciente();

        odontologo.setId(turnoDTO.getOdontologoId());
        paciente.setId(turnoDTO.getPacienteId());

        respuesta.setId(turnoDTO.getId());
        respuesta.setFecha(turnoDTO.getFecha());
        respuesta.setOdontologo(odontologo);
        respuesta.setPaciente(paciente);
        return respuesta;
    }
}
