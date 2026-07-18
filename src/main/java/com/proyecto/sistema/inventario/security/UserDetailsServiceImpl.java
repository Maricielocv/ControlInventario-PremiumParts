package com.proyecto.sistema.inventario.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyecto.sistema.inventario.entities.Empleado;
import com.proyecto.sistema.inventario.repository.EmpleadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Empleado empleado = empleadoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Empleado no encontrado con email: " + email));

        return new User(
                empleado.getEmail(),
                empleado.getPassword(),
                List.of(new SimpleGrantedAuthority(empleado.getRol().name())));
    }
}
