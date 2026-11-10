
package br.inatel.cdg.service;
import br.inatel.cdg.reserva.Reserva;

public class HotelService {
    private Reserva[] reservas;
    private int quantidadeReservas;
    private double faturamento;

    public HotelService() {
        reservas = new Reserva[100];
        quantidadeReservas = 0;
    }

    public void cadastrarReserva(Reserva reserva) {
        if (quantidadeReservas < 100) {
            reservas[quantidadeReservas] = reserva;
            quantidadeReservas++;
            System.out.println("Reserva cadastrada com sucesso!");
        } else {
            System.out.println("Limite de reservas atingido!");
        }
    }

    public void listarReservas() {
        if (quantidadeReservas == 0) {
            System.out.println("Nenhuma reserva cadastrada!");
        } else {
            for (int i = 0; i < quantidadeReservas; i++) {
                System.out.println("----------------------");
                System.out.println("ID da reserva: " + reservas[i].getId());
                System.out.println("Hospede: " + reservas[i].getHospede().getNome());
                System.out.println("Quarto: " + reservas[i].getQuarto().getNumero());
                System.out.println("Data de entrada: " + reservas[i].getDataEntrada());
                System.out.println("Data de saida: " + reservas[i].getDataSaida());
                System.out.println("Quantidade de ocupantes: " + reservas[i].getQuantidadeOcupantes());
                System.out.println("Valor total: " + reservas[i].getValorTotal());
                System.out.println("Status: " + reservas[i].getStatus());
            }
        }
    }

    public void realizarCheckIn(int id) {
        for (int i = 0; i < quantidadeReservas; i++) {
            if (reservas[i].getId() == id) {
             switch (reservas[i].getStatus()) {
                case "RESERVADA":
                    reservas[i].realizarCheckIn();
                    System.out.println("Check-in realizado com sucesso!");
                    break;
                case "EM ESTADIA":
                    System.out.println("O check-in ja foi realizado!");
                    break;
                case "FINALIZADA":
                    System.out.println("Essa reserva ja foi finalizada!");
                    break;
                }
            return;
            }
        }
     System.out.println("Reserva nao encontrada!");
    }

    public void realizarCheckOut(int id) {
        for (int i = 0; i < quantidadeReservas; i++) {
            if (reservas[i].getId() == id) {
             switch (reservas[i].getStatus()) {
                case "RESERVADA":
                    System.out.println("O hospede ainda nao realizou o check-in!");
                    break;

                case "EM_ESTADIA":
                    reservas[i].setStatus("FINALIZADA");
                    System.out.println("Check-out realizado com sucesso!");
                    break;

                case "FINALIZADA":
                    System.out.println("O check-out ja foi realizado!");
                    break;
                }
                return;
            }
        }
        System.out.println("Reserva nao encontrada!");
    }

    public double calcularFaturamento() {
        faturamento = 0;
        for (int i = 0; i < quantidadeReservas; i++) {
            switch (reservas[i].getStatus()) {
                case "FINALIZADA":
                 faturamento = faturamento + reservas[i].getValorTotal();
                break;
            }

        }
        return faturamento;
    }

}
