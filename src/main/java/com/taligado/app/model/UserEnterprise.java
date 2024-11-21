package com.taligado.app.model;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user_enterprise")
public class UserEnterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome da empresa não pode ser nulo")
    @Size(min = 1, max = 100, message = "O nome da empresa deve ter entre 1 e 100 caracteres")
    private String nome;

    @NotNull(message = "O nome de usuário não pode ser nulo")
    @Size(min = 1, max = 50, message = "O nome de usuário deve ter entre 1 e 50 caracteres")
    private String username;

    @NotNull(message = "A senha não pode ser nula")
    private String password;

    private String imgPerfil;

    @NotNull(message = "O CNPJ não pode ser nulo")
    private String cnpj;

    @NotNull(message = "O e-mail não pode ser nulo")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    public void updateImgPerfil(String imgPerfilUrl) {
        if (imgPerfilUrl != null && !imgPerfilUrl.isEmpty()) {
            this.imgPerfil = imgPerfilUrl;
        }
    }
}
