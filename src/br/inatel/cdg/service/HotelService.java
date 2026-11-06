package br.inatel.cdg.service;
import br.inatel.cdg.reserva.Reserva;

public class HotelService{
    private Reserva[] reservas;
    private int quantidadeReservas;

    public HotelService(){
        reservas = new Reservas[100];
        quantidadeReservas = 0;
    }

    public void cadastrarReserva(Reserva reserva){
        if(quantidadeReservas < 100){
            reservas[quantidadeReservas] = reserva;
            quantidadeReservas++;
            System.out.println("Reserva cadastrada com sucesso!");
        }else{
            System.out.println("Limite de reservas atingido!");
        }
    }

    public void listarReservas(){
        if(quantidadeReservas == 0){
            System.out.println("Nenhuma reserva cadastrada");
        }else{
            for(int i = 0; i < quantidadeReservas; i++){
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
}