package com.ClinicaOdontologicaCHUTT.service;

import com.ClinicaOdontologicaCHUTT.entity.Usuario;
import com.ClinicaOdontologicaCHUTT.repository.IUsuarioRepository;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    private static final Logger LOGGER = Logger.getLogger(UsuarioService.class);
    private final IUsuarioRepository usuarioRepository;

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByEmail(username);
        if (usuarioBuscado.isPresent()){
            return usuarioBuscado.get();
        }else {
            LOGGER.warn("No existe un usuario con ese correo electronico");
            throw new UsernameNotFoundException("No existe un usuario con ese correo electronico");
        }

    }
}
