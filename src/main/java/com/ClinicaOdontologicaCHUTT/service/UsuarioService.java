package com.ClinicaOdontologicaCHUTT.service;

import com.ClinicaOdontologicaCHUTT.entity.Usuario;
import com.ClinicaOdontologicaCHUTT.entity.UsuarioRole;
import com.ClinicaOdontologicaCHUTT.repository.IUsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService implements UserDetailsService {
    private final IUsuarioRepository usuarioRepository;

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByEmail(username);
        Set<GrantedAuthority> rolesDeUsuario = new HashSet<>();

        for (UsuarioRole rol: usuarioBuscado.get().getUsuarioRole() ) {

        }
        return null;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByEmail(username);
        if (usuarioBuscado.isPresent()){
            return usuarioBuscado.get();
        }else {
            throw new UsernameNotFoundException("No existe un usuario con ese correo electronico");
        }

    }
}
