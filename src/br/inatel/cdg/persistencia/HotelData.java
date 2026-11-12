package br.inatel.cdg.persistencia;

import br.inatel.cdg.hospede.Hospede;
import br.inatel.cdg.quarto.Quarto;
import br.inatel.cdg.reserva.Reserva;
import java.io.Serializable;

public class HotelData implements Serializable {

    private Hospede[] hospedes = new Hospede[100];
    private Quarto[] quartos = new Quarto[100];
    private Reserva[] reservas = new Reserva[100];

    private int totalHospedes = 0;
    private int totalQuartos = 0;
    private int totalReservas = 0;

    private int proximoIdHospede = 1;
    private int proximoIdReserva = 1;

    public int gerarIdHospede() {
        return proximoIdHospede++;
    }

    public int gerarIdReserva() {
        return proximoIdReserva++;
    }

    public void adicionarHospede(Hospede hospede) {
        if (hospede == null || totalHospedes >= hospedes.length) {
            throw new IllegalArgumentException("Nao foi possivel cadastrar hospede.");
        }

        for (int i = 0; i < totalHospedes; i++) {
            if (hospedes[i].getId() == hospede.getId()
                    || hospedes[i].getDocumento().equals(hospede.getDocumento())) {
                throw new IllegalArgumentException("Hospede ja cadastrado.");
            }
        }

        hospedes[totalHospedes] = hospede;
        totalHospedes++;

        if (hospede.getId() >= proximoIdHospede) {
            proximoIdHospede = hospede.getId() + 1;
        }
    }

    public void adicionarQuarto(Quarto quarto) {
        if (quarto == null || totalQuartos >= quartos.length) {
            throw new IllegalArgumentException("Nao foi possivel cadastrar quarto.");
        }

        for (int i = 0; i < totalQuartos; i++) {
            if (quartos[i].getNumero() == quarto.getNumero()) {
                throw new IllegalArgumentException("Quarto ja cadastrado.");
            }
        }

        quartos[totalQuartos] = quarto;
        totalQuartos++;
    }

    public void adicionarReserva(Reserva reserva) {
        if (reserva == null || totalReservas >= reservas.length) {
            throw new IllegalArgumentException("Nao foi possivel cadastrar reserva.");
        }

        reservas[totalReservas] = reserva;
        totalReservas++;
    }

    public Hospede[] getHospedes() {
        return hospedes;
    }

    public Quarto[] getQuartos() {
        return quartos;
    }

    public Reserva[] getReservas() {
        return reservas;
    }

    public int getTotalHospedes() {
        return totalHospedes;
    }

    public int getTotalQuartos() {
        return totalQuartos;
    }

    public int getTotalReservas() {
        return totalReservas;
    }
}