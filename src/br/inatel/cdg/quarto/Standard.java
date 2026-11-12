package br.inatel.cdg.quarto;

public class Standard extends Quarto {

    public Standard(int numero, int capacidade, double valorBase) {
        super(numero, capacidade, valorBase);
    }

    @Override
    public double calcularValor(int diarias) {
        validarDiarias(diarias);
        return getValorBase() * diarias;
    }
}