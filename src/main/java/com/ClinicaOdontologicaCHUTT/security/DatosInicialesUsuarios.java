package com.ClinicaOdontologicaCHUTT.security;

import com.ClinicaOdontologicaCHUTT.entity.Usuario;
import com.ClinicaOdontologicaCHUTT.entity.UsuarioRole;
import com.ClinicaOdontologicaCHUTT.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosInicialesUsuarios implements ApplicationRunner {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String clave = "12345";
        String claveCifrada = encoder.encode(clave);
        Usuario usuarioAInsertar = new Usuario("Mateo","mateobr0","mateo@gmail.com", claveCifrada, UsuarioRole.ROLE_USER);
        Usuario usuarioAdminAInsertar = new Usuario("Admin","adminbr0","admin@gmail.com", claveCifrada, UsuarioRole.ROLE_ADMIN);

        usuarioRepository.save(usuarioAInsertar);
        usuarioRepository.save(usuarioAdminAInsertar);
    }
}
