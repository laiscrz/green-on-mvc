package com.taligado.app.service;

import com.taligado.app.model.Role;
import com.taligado.app.model.UserEnterprise;
import com.taligado.app.repository.IRoleRepository;
import com.taligado.app.repository.IUserEnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserEnterpriseService {

    @Autowired
    private IUserEnterpriseRepository userEnterpriseRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUser(UserEnterprise userEnterprise, Long idRole) {

        userEnterprise.setPassword(passwordEncoder.encode(userEnterprise.getPassword()));

        Set<Role> roles = new HashSet<>();

        if (idRole != null) {
            Role role = roleRepository.findById(idRole)
                    .orElseThrow(() -> new IllegalArgumentException("Role não encontrada"));
            roles.add(role);
        } else {
            Role defaultRole = roleRepository.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("Role padrão não encontrada"));
            roles.add(defaultRole);
        }

        userEnterprise.setRoles(roles);
        userEnterpriseRepository.save(userEnterprise);
    }


    public Set<Role> getAllRoles() {
        Set<Role> roles = new HashSet<>(roleRepository.findAll());
        System.out.println("Roles carregadas: " + roles);
        return roles;
    }

    public Optional<UserEnterprise> getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userEnterpriseRepository.findByUsername(username);
    }

    public Optional<UserEnterprise> getUserByUsername(String username) {
        return userEnterpriseRepository.findByUsername(username);
    }

    public void saveUserEnterprise(UserEnterprise userEnterprise) {
        userEnterpriseRepository.save(userEnterprise);
    }

    public void updateUser(UserEnterprise userEnterprise) {
        Optional<UserEnterprise> existingUserOpt = userEnterpriseRepository.findById(userEnterprise.getId());

        if (existingUserOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        UserEnterprise existingUser = existingUserOpt.get();

        userEnterprise.setId(existingUser.getId()); 

        if (userEnterprise.getNome() != null && !userEnterprise.getNome().isEmpty()) {
            existingUser.setNome(userEnterprise.getNome());
        }

        if (userEnterprise.getEmail() != null && !userEnterprise.getEmail().isEmpty()) {
            existingUser.setEmail(userEnterprise.getEmail());
        }

        if (userEnterprise.getImgPerfil() != null && !userEnterprise.getImgPerfil().isEmpty()) {
            existingUser.setImgPerfil(userEnterprise.getImgPerfil());
        }

        if (userEnterprise.getPassword() != null && !userEnterprise.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userEnterprise.getPassword()));
        }

        userEnterpriseRepository.save(existingUser);
    }


    public void deleteUser(String username) {
        Optional<UserEnterprise> userEnterpriseOptional = userEnterpriseRepository.findByUsername(username);

        if (userEnterpriseOptional.isPresent()) {
            userEnterpriseRepository.delete(userEnterpriseOptional.get());
        } else {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
    }

}
