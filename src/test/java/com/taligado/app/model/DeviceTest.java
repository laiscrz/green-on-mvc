package com.taligado.app.model;

import com.taligado.app.model.enums.DepartmentType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDevice() {
        Device device = new Device();
        device.setNome("Dispositivo Teste");
        device.setDepartamento(DepartmentType.PEQUISA);
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setImagemURL("http://example.com/image.png");
        device.setFatorEmissao(0.5);

        Set<ConstraintViolation<Device>> violations = validator.validate(device);
        assertThat(violations).isEmpty();
    }

    @Test
    void testNomeNotEmpty() {
        Device device = new Device();
        device.setNome(null);
        device.setDepartamento(DepartmentType.PEQUISA);
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setImagemURL("http://example.com/image.png");
        device.setFatorEmissao(0.5);

        Set<ConstraintViolation<Device>> violations = validator.validate(device);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome do dispositivo não pode ser nulo");
    }

    @Test
    void testDepartamentoNotNull() {
        Device device = new Device();
        device.setNome("Dispositivo Teste");
        device.setDepartamento(null);
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setImagemURL("http://example.com/image.png");
        device.setFatorEmissao(0.5);

        Set<ConstraintViolation<Device>> violations = validator.validate(device);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O departamento não pode ser nulo");
    }

    @Test
    void testPotenciaNominalNotNull() {
        Device device = new Device();
        device.setNome("Dispositivo Teste");
        device.setDepartamento(DepartmentType.PEQUISA);
        device.setPotenciaNominal(null);
        device.setTempoUso(5.0);
        device.setImagemURL("http://example.com/image.png");
        device.setFatorEmissao(0.5);

        Set<ConstraintViolation<Device>> violations = validator.validate(device);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("A potência nominal não pode ser nula");
    }

    @Test
    void testTempoUsoNotNull() {
        Device device = new Device();
        device.setNome("Dispositivo Teste");
        device.setDepartamento(DepartmentType.PEQUISA);
        device.setPotenciaNominal(100.0);
        device.setTempoUso(null);
        device.setImagemURL("http://example.com/image.png");
        device.setFatorEmissao(0.5);

        Set<ConstraintViolation<Device>> violations = validator.validate(device);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O tempo de uso não pode ser nulo");
    }

    @Test
    void testImagemURLValid() {
        Device device = new Device();
        device.setNome("Dispositivo Teste");
        device.setDepartamento(DepartmentType.PEQUISA);
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setImagemURL("invalid-url");
        device.setFatorEmissao(0.5);

        Set<ConstraintViolation<Device>> violations = validator.validate(device);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O formato da URL da imagem é inválido");
    }

    @Test
    void testFatorEmissaoNotNull() {
        Device device = new Device();
        device.setNome("Dispositivo Teste");
        device.setDepartamento(DepartmentType.PEQUISA);
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setImagemURL("http://example.com/image.png");
        device.setFatorEmissao(null);

        Set<ConstraintViolation<Device>> violations = validator.validate(device);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O fator de emissão não pode ser nulo");
    }

    @Test
    void testFatorEmissaoPositive() {
        Device device = new Device();
        device.setNome("Dispositivo Teste");
        device.setDepartamento(DepartmentType.PEQUISA);
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setImagemURL("http://example.com/image.png");
        device.setFatorEmissao(-0.1);

        Set<ConstraintViolation<Device>> violations = validator.validate(device);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O fator de emissão deve ser um número positivo");
    }

    @Test
    void testValidDeviceEmissionCalculation() {
        Device device = new Device();
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setFatorEmissao(0.2);

        double expectedEmission = device.getPotenciaNominal() * device.getTempoUso() * device.getFatorEmissao();
        assertThat(expectedEmission).isEqualTo(100.0 * 5.0 * 0.2); // 100 * 5 * 0.2 = 100.0
    }
}

