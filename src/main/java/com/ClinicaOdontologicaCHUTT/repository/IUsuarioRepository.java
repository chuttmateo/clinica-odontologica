package com.ClinicaOdontologicaCHUTT.repository;

import com.ClinicaOdontologicaCHUTT.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

}
