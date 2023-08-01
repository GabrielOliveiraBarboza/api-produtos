package com.example.springboot.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name="TB PRODUTOS")
public class ProdutoModel extends RepresentationModel<ProdutoModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID idprodutos;
    private String name;
    private BigDecimal value;

    public UUID getIdprodutos() {
        return idprodutos;
    }

    public void setIdprodutos(UUID idprodutos) {
        this.idprodutos = idprodutos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
