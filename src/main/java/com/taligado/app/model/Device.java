package com.taligado.app.model;

import com.taligado.app.model.enums.DepartmentType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private DepartmentType departments;

    private Double potenciaNominal;

    private Double tempoUso;

    private String imagemURL;

    private Double fatorEmissao;

    @ManyToMany(mappedBy = "dispositivos")
    private List<BranchOffice> filiais;

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", departments=" + departments +
                ", potenciaNominal=" + potenciaNominal +
                ", tempoUso=" + tempoUso +
                ", imagemURL='" + imagemURL + '\'' +
                ", fatorEmissao=" + fatorEmissao +
                '}';
    }
}
