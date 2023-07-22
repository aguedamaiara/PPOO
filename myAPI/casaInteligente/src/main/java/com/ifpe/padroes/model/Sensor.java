package com.ifpe.padroes.model;

import jakarta.persistence.*;


@Entity
@Table(name = "sensores")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nome;
    private double valor;
    private String estado;

    @Enumerated(EnumType.STRING)
    private TipoSensor tipo;

    @ManyToOne
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    private User autor;

    public Sensor() { }

    public Sensor(String nome, double valor, String estado, TipoSensor tipo, User autor) {
        this.nome = nome;
        this.valor = valor;
        this.estado = estado;
        this.tipo = tipo;
        this.autor = autor;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TipoSensor getTipo() {
        return tipo;
    }

    public void setTipo(TipoSensor tipo) {
        this.tipo = tipo;
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }
}
