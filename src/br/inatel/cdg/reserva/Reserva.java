
package br.inatel.cdg.reserva;

import br.inatel.cdg.hospede.Hospede;
import br.inatel.cdg.quarto.Quarto;
import java.io.Serializable;

public class Reserva implements Serializable {

    private static final long serialVersionUID = 8212948588317806864L;

    private int id;
    private Hospede hospede;
    private Quarto quarto;
    private String dataEntrada;
    private String dataSaida;
    private int quantidadeOcupantes;
    private double valorTotal;
    private String status;

    public Reserva(int id, Hospede hospede, Quarto quarto,
                   String dataEntrada, String dataSaida, int quantidadeOcupantes) {

        this.id = id;
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.quantidadeOcupantes = quantidadeOcupantes;
        this.valorTotal = 0;
        this.status = "RESERVADA";
    }

    public void realizarCheckIn() {
        status = "EM ESTADIA";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public int getQuantidadeOcupantes() {
        return quantidadeOcupantes;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getStatus() {
        return status;
    }
}
