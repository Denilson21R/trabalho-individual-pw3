package com.example.pw3trabalhoindividual.model;

import javax.persistence.*;

@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne()
    @JoinColumn(name = "especie_id")
    private Especie especie;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.ORDINAL)
    private Tamanho tamanho;

    private String observacao;

    @Column(nullable = false)
    private String nome_dono;


    public Animal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getNome_dono() {
        return nome_dono;
    }

    public void setNome_dono(String nome_done) {
        this.nome_dono = nome_done;
    }
}
