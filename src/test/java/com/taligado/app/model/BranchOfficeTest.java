package com.taligado.app.model;

import com.taligado.app.model.enums.SegmentType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BranchOfficeTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidBranchOffice() {
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setNome("Filial Teste");
        branchOffice.setEndereco("Rua Teste, 123");
        branchOffice.setTelefone("123456789");
        branchOffice.setSegmento(SegmentType.INDUSTRIA);

        Device device = new Device();
        device.setId(1L);
        device.setNome("Dispositivo Teste");
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setFatorEmissao(0.2);

        branchOffice.setDispositivos(List.of(device));

        Set<ConstraintViolation<BranchOffice>> violations = validator.validate(branchOffice);
        assertThat(violations).isEmpty();
    }

    @Test
    void testNomeNotEmpty() {
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setNome(null);
        branchOffice.setEndereco("Rua Teste, 123");
        branchOffice.setTelefone("123456789");
        branchOffice.setSegmento(SegmentType.INDUSTRIA);

        Device device = new Device();
        device.setId(1L);
        device.setNome("Dispositivo Teste");
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setFatorEmissao(0.2);

        branchOffice.setDispositivos(List.of(device));

        Set<ConstraintViolation<BranchOffice>> violations = validator.validate(branchOffice);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome da filial não pode ser nulo");
    }


    @Test
    void testEnderecoNotEmpty() {
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setNome("Filial Teste");
        branchOffice.setEndereco(null);
        branchOffice.setTelefone("123456789");
        branchOffice.setSegmento(SegmentType.INDUSTRIA);

        Device device = new Device();
        device.setId(1L);
        device.setNome("Dispositivo Teste");
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setFatorEmissao(0.2);

        branchOffice.setDispositivos(List.of(device));

        Set<ConstraintViolation<BranchOffice>> violations = validator.validate(branchOffice);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O endereço da filial não pode ser nulo");
    }

    @Test
    void testTelefoneNotEmpty() {
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setNome("Filial Teste");
        branchOffice.setEndereco("Rua Teste, 123");
        branchOffice.setTelefone(null);
        branchOffice.setSegmento(SegmentType.INDUSTRIA);

        Device device = new Device();
        device.setId(1L);
        device.setNome("Dispositivo Teste");
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setFatorEmissao(0.2);

        branchOffice.setDispositivos(List.of(device));

        Set<ConstraintViolation<BranchOffice>> violations = validator.validate(branchOffice);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O telefone da filial não pode ser nulo");
    }

    @Test
    void testSegmentoNotNull() {
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setNome("Filial Teste");
        branchOffice.setEndereco("Rua Teste, 123");
        branchOffice.setTelefone("123456789");
        branchOffice.setSegmento(null);

        Device device = new Device();
        device.setId(1L);
        device.setNome("Dispositivo Teste");
        device.setPotenciaNominal(100.0);
        device.setTempoUso(5.0);
        device.setFatorEmissao(0.2);

        branchOffice.setDispositivos(List.of(device));

        Set<ConstraintViolation<BranchOffice>> violations = validator.validate(branchOffice);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O segmento não pode ser nulo");
    }

    @Test
    void testCalcularConsumoEnergia() {
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setNome("Filial Teste");
        branchOffice.setEndereco("Rua Teste, 123");
        branchOffice.setTelefone("123456789");
        branchOffice.setSegmento(SegmentType.INDUSTRIA);

        Device device1 = new Device();
        device1.setId(1L);
        device1.setNome("Dispositivo 1");
        device1.setPotenciaNominal(100.0);
        device1.setTempoUso(5.0);

        Device device2 = new Device();
        device2.setId(2L);
        device2.setNome("Dispositivo 2");
        device2.setPotenciaNominal(200.0);
        device2.setTempoUso(3.0);

        branchOffice.setDispositivos(List.of(device1, device2));

        branchOffice.calcularConsumoEnergia();
        assertThat(branchOffice.getConsumoEnergia()).isEqualTo(100.0 * 5.0 + 200.0 * 3.0); // 500 + 600 = 1100
    }

    @Test
    void testCalcularEmissoesCarbono() {
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setNome("Filial Teste");
        branchOffice.setEndereco("Rua Teste, 123");
        branchOffice.setTelefone("123456789");
        branchOffice.setSegmento(SegmentType.INDUSTRIA);

        Device device1 = new Device();
        device1.setId(1L);
        device1.setNome("Dispositivo 1");
        device1.setPotenciaNominal(100.0);
        device1.setTempoUso(5.0);
        device1.setFatorEmissao(0.2);

        Device device2 = new Device();
        device2.setId(2L);
        device2.setNome("Dispositivo 2");
        device2.setPotenciaNominal(200.0);
        device2.setTempoUso(3.0);
        device2.setFatorEmissao(0.3);

        branchOffice.setDispositivos(List.of(device1, device2));
        branchOffice.calcularConsumoEnergia();
        branchOffice.calcularEmissoesCarbono();

        double expectedEmissions = (100.0 * 5.0 + 200.0 * 3.0) * (0.2 + 0.3); // 1100 * 0.5 = 550
        assertThat(branchOffice.getEmissoesCarbono()).isEqualTo(expectedEmissions);
    }
}
