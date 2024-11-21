package com.taligado.app.model;

import com.taligado.app.model.enums.SegmentType;
import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.util.List;

@Data
@Entity
@Table(name = "branch_office")
public class BranchOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome da filial não pode ser nulo")
    @Size(min = 1, max = 100, message = "O nome da filial deve ter entre 1 e 100 caracteres")
    private String nome;

    @NotNull(message = "O endereço da filial não pode ser nulo")
    @Size(min = 1, max = 200, message = "O endereço da filial deve ter entre 1 e 200 caracteres")
    private String endereco;

    @NotNull(message = "O telefone da filial não pode ser nulo")
    private String telefone;

    @NotNull(message = "O segmento não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private SegmentType segmento;


    @ManyToMany
    @JoinTable(
            name = "branch_office_device",
            joinColumns = @JoinColumn(name = "branch_office_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id")
    )
    private List<Device> dispositivos;

    private Double consumoEnergia;

    private Double emissoesCarbono;

    // Método para calcular o consumo total de energia
    public void calcularConsumoEnergia() {
        consumoEnergia = dispositivos.stream()
                .mapToDouble(device -> device.getPotenciaNominal() * device.getTempoUso())
                .sum();
    }

    // Método para calcular as emissões de carbono (em toneladas de CO2)
    public void calcularEmissoesCarbono() {
        emissoesCarbono = dispositivos.stream()
                .mapToDouble(device -> consumoEnergia * device.getFatorEmissao())
                .sum();
    }
}
