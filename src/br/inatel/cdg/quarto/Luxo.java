package br.inatel.cdg.quarto;

public class Luxo extends Quarto {

    public Luxo(int numero, int capacidade, double valorBase) {
        super(numero, capacidade, valorBase);
    }

    @Override
    public double calcularValor(int diarias) {
        validarDiarias(diarias);
        return getValorBase() * diarias * 1.20;
    }
}