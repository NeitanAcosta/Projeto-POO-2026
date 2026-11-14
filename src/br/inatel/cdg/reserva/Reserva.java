package br.inatel.cdg.reserva;
import br.inatel.cdg.hospede.Hospede;
import br.inatel.cdg.quarto.Quarto;
import java.io.Serializable;

public class Reserva implements Serializable {

    private int id;
    private Hospede hospede;
    private Quarto quarto;
    private String dataEntrada;
    private String dataSaida;
    private int quantidadeOcupantes;
    private double valorTotal;
    private String status;

    public Reserva(int id, Hospede hospede, Quarto quarto,String dataEntrada, String dataSaida,int quantidadeOcupantes) {
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
        this.status = "EM ESTADIA";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public int getQuantidadeOcupantes() {
        return quantidadeOcupantes;
    }

    public void setQuantidadeOcupantes(int quantidadeOcupantes) {
        this.quantidadeOcupantes = quantidadeOcupantes;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
