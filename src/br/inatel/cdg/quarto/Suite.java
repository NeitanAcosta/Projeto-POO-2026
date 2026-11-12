package br.inatel.cdg.quarto;

public class Suite extends Quarto {

    public Suite(int numero, int capacidade, double valorBase) {
        super(numero, capacidade, valorBase);
    }

    @Override
    public double calcularValor(int diarias) {
        validarDiarias(diarias);
        return getValorBase() * diarias * 1.50;
    }
}