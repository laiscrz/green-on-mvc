package com.taligado.app.model;

import com.taligado.app.model.enums.SegmentType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "branch_office")
public class BranchOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String endereco;

    private String telefone;

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
                .mapToDouble(device -> consumoEnergia * device.getFatorEmissao()) // Consumo total * Fator de emissão do dispositivo
                .sum();
    }
}
