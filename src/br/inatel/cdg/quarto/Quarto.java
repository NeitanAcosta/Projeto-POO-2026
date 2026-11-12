package br.inatel.cdg.quarto;

import java.io.Serializable;

public abstract class Quarto implements Precificavel, Serializable {

    private int numero;
    private int capacidade;
    private double valorBase;

    public Quarto(int numero, int capacidade, double valorBase) {
        if (numero <= 0 || capacidade <= 0 || valorBase <= 0) {
            throw new IllegalArgumentException("Dados do quarto invalidos.");
        }

        this.numero = numero;
        this.capacidade = capacidade;
        this.valorBase = valorBase;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public double getValorBase() {
        return valorBase;
    }

    public boolean permiteOcupantes(int quantidade) {
        return quantidade > 0 && quantidade <= capacidade;
    }

    protected void validarDiarias(int diarias) {
        if (diarias <= 0) {
            throw new IllegalArgumentException("Quantidade de diarias invalida.");
        }
    }

    public abstract double calcularValor(int diarias);
}