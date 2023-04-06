package com.ClinicaOdontologicaCHUTT.service;

import com.ClinicaOdontologicaCHUTT.entity.Odontologo;
import com.ClinicaOdontologicaCHUTT.exception.ResourceNotFoundException;
import com.ClinicaOdontologicaCHUTT.repository.IOdontologoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private final IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {

        return odontologoRepository.save(odontologo);
    }

    public Odontologo actualizarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        if (buscarOdontologoPorId(id).isPresent()) {
            odontologoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("No se puede eliminar ese odontologo porque no existe en la db");
        }
    }

    public Optional<Odontologo> buscarOdontologoPorId(Long id) {
        return odontologoRepository.findById(id);
    }

    public Optional<Odontologo> buscarPorMatricula(String matricula) {
        return odontologoRepository.findByMatricula(matricula);
    }

    public List<Odontologo> listarOdontologos() {
        return odontologoRepository.findAll();
    }
}
