package com.taligado.app.model;

import com.taligado.app.model.enums.DepartmentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Data
@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome do dispositivo não pode ser nulo")
    @Size(min = 1, max = 100, message = "O nome do dispositivo deve ter entre 1 e 100 caracteres")
    private String nome;

    @NotNull(message = "O departamento não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private DepartmentType departamento;

    @NotNull(message = "A potência nominal não pode ser nula")
    @Positive(message = "A potência nominal deve ser um número positivo")
    private Double potenciaNominal;

    @NotNull(message = "O tempo de uso não pode ser nulo")
    @PositiveOrZero(message = "O tempo de uso não pode ser negativo")
    private Double tempoUso;

    @URL(message = "O formato da URL da imagem é inválido")
    private String imagemURL;

    @NotNull(message = "O fator de emissão não pode ser nulo")
    @Positive(message = "O fator de emissão deve ser um número positivo")
    private Double fatorEmissao;

    @ManyToMany(mappedBy = "dispositivos")
    private List<BranchOffice> filiais;

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", departamento=" + departamento +
                ", potenciaNominal=" + potenciaNominal +
                ", tempoUso=" + tempoUso +
                ", imagemURL='" + imagemURL + '\'' +
                ", fatorEmissao=" + fatorEmissao +
                '}';
    }
}
