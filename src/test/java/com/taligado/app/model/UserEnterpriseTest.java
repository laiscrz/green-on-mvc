package com.taligado.app.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserEnterpriseTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUserEnterprise() {
        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setNome("Empresa Teste");
        userEnterprise.setUsername("empresa_teste");
        userEnterprise.setPassword("senhaSegura123");
        userEnterprise.setCnpj("12345678000199");
        userEnterprise.setEmail("empresa@teste.com");

        Role role = new Role();
        role.setId(1L);
        userEnterprise.setRoles(Set.of(role));

        Set<ConstraintViolation<UserEnterprise>> violations = validator.validate(userEnterprise);
        assertThat(violations).isEmpty();
    }

    @Test
    void testNomeNotEmpty() {
        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setNome(null);
        userEnterprise.setUsername("empresa_teste");
        userEnterprise.setPassword("senhaSegura123");
        userEnterprise.setCnpj("12345678000199");
        userEnterprise.setEmail("empresa@teste.com");

        Set<ConstraintViolation<UserEnterprise>> violations = validator.validate(userEnterprise);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome da empresa não pode ser nulo");
    }


    @Test
    void testUsernameNotEmpty() {
        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setNome("Empresa Teste");
        userEnterprise.setUsername(null);
        userEnterprise.setPassword("senhaSegura123");
        userEnterprise.setCnpj("12345678000199");
        userEnterprise.setEmail("empresa@teste.com");

        Set<ConstraintViolation<UserEnterprise>> violations = validator.validate(userEnterprise);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome de usuário não pode ser nulo");
    }

    @Test
    void testCnpjNotEmpty() {
        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setNome("Empresa Teste");
        userEnterprise.setUsername("empresa_teste");
        userEnterprise.setPassword("senhaSegura123");
        userEnterprise.setCnpj(null);
        userEnterprise.setEmail("empresa@teste.com");

        Set<ConstraintViolation<UserEnterprise>> violations = validator.validate(userEnterprise);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O CNPJ não pode ser nulo");
    }

    @Test
    void testEmailNotEmpty() {
        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setNome("Empresa Teste");
        userEnterprise.setUsername("empresa_teste");
        userEnterprise.setPassword("senhaSegura123");
        userEnterprise.setCnpj("12345678000199");
        userEnterprise.setEmail(null);

        Set<ConstraintViolation<UserEnterprise>> violations = validator.validate(userEnterprise);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O e-mail não pode ser nulo");
    }

    @Test
    void testPasswordNotNull() {
        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setNome("Empresa Teste");
        userEnterprise.setUsername("empresa_teste");
        userEnterprise.setPassword(null);
        userEnterprise.setCnpj("12345678000199");
        userEnterprise.setEmail("empresa@teste.com");

        Set<ConstraintViolation<UserEnterprise>> violations = validator.validate(userEnterprise);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("A senha não pode ser nula");
    }
}
