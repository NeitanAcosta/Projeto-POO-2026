package br.inatel.cdg.service;

import br.inatel.cdg.Exception.*;
import br.inatel.cdg.hospede.*;
import br.inatel.cdg.persistencia.*;
import br.inatel.cdg.quarto.*;
import br.inatel.cdg.reserva.*;

import java.time.*;
import java.time.format.*;
import java.time.temporal.*;

public class HotelService {
    private HotelData dados;
    private PersistenciaArquivo persistencia;

    public HotelService() {
        persistencia = new PersistenciaArquivo();
        dados = persistencia.carregar();

        // Dados de exemplo para testar o sistema.
        if (dados.getTotalHospedes() == 0) {
            cadastrarHospede("Joao Silva", "123456789", "11999999999");
            cadastrarHospede("Maria Souza", "987654321", "11988888888");
        }
        if (dados.getTotalQuartos() == 0) {
            cadastrarQuarto(101, 2, 150, "STANDARD");
            cadastrarQuarto(102, 3, 200, "LUXO");
            cadastrarQuarto(103, 4, 300, "SUITE");
        }
    }

    public void cadastrarHospede(String nome, String documento, String telefone) {
        int id = dados.gerarIdHospede();
        dados.adicionarHospede(new Hospede(id, nome, documento, telefone));
        persistencia.salvar(dados);
        System.out.println("Hospede cadastrado! ID: " + id);
    }

    public void cadastrarQuarto(int numero, int capacidade, double valorBase, String tipo) {
        Quarto quarto;
        switch (tipo) {
            case "STANDARD": quarto = new Standard(numero, capacidade, valorBase); break;
            case "LUXO": quarto = new Luxo(numero, capacidade, valorBase); break;
            case "SUITE": quarto = new Suite(numero, capacidade, valorBase); break;
            default: throw new IllegalArgumentException("Tipo de quarto invalido!");
        }
        dados.adicionarQuarto(quarto);
        persistencia.salvar(dados);
    }

    public Hospede buscarHospede(int id) {
        for (int i = 0; i < dados.getTotalHospedes(); i++) {
            Hospede hospede = dados.getHospedes()[i];
            if (hospede.getId() == id) return hospede;
        }
        return null;
    }

    public Quarto buscarQuarto(int numero) {
        for (int i = 0; i < dados.getTotalQuartos(); i++) {
            Quarto quarto = dados.getQuartos()[i];
            if (quarto.getNumero() == numero) return quarto;
        }
        return null;
    }

    // Converte textos como 15/11/2026 em datas validas.
    private LocalDate lerData(String texto) throws ValidacaoException {
        try {
            if (texto != null && texto.contains("-")) return LocalDate.parse(texto);
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            return LocalDate.parse(texto, formato);
        } catch (Exception e) {
            throw new ValidacaoException("Data invalida! Use dd/MM/aaaa.");
        }
    }

    public void cadastrarReserva(int idHospede, int numeroQuarto,
                                 String dataEntrada, String dataSaida,
                                 int quantidadeOcupantes) throws ValidacaoException {
        Hospede hospede = buscarHospede(idHospede);
        Quarto quarto = buscarQuarto(numeroQuarto);
        if (hospede == null) throw new ValidacaoException("Hospede nao encontrado!");
        if (quarto == null) throw new ValidacaoException("Quarto nao encontrado!");
        if (!quarto.permiteOcupantes(quantidadeOcupantes)) {
            throw new ValidacaoException("Quantidade de ocupantes invalida!");
        }

        LocalDate entrada = lerData(dataEntrada);
        LocalDate saida = lerData(dataSaida);
        int diarias = (int) ChronoUnit.DAYS.between(entrada, saida);
        if (diarias <= 0) throw new ValidacaoException("A saida deve ser depois da entrada!");

        for (int i = 0; i < dados.getTotalReservas(); i++) {
            Reserva atual = dados.getReservas()[i];
            if (atual.getQuarto().getNumero() == numeroQuarto
                    && !atual.getStatus().equals("FINALIZADA")
                    && !atual.getStatus().equals("CANCELADA")) {
                LocalDate inicioAtual = lerData(atual.getDataEntrada());
                LocalDate fimAtual = lerData(atual.getDataSaida());
                if (entrada.isBefore(fimAtual) && saida.isAfter(inicioAtual)) {
                    throw new ValidacaoException("Quarto ocupado nesse periodo!");
                }
            }
        }

        int id = dados.gerarIdReserva();
        Reserva reserva = new Reserva(id, hospede, quarto,
                dataEntrada, dataSaida, quantidadeOcupantes);
        reserva.setValorTotal(quarto.calcularValor(diarias));
        dados.adicionarReserva(reserva);
        persistencia.salvar(dados);
        System.out.println("Reserva cadastrada! ID: " + id);
    }

    public void listarQuartos() {
        for (int i = 0; i < dados.getTotalQuartos(); i++) {
            Quarto quarto = dados.getQuartos()[i];
            System.out.println("Quarto " + quarto.getNumero() + " - "
                    + quarto.getClass().getSimpleName() + " - R$ " + quarto.getValorBase());
        }
    }

    public void listarReservas() {
        if (dados.getTotalReservas() == 0) System.out.println("Nenhuma reserva cadastrada.");
        for (int i = 0; i < dados.getTotalReservas(); i++) {
            Reserva r = dados.getReservas()[i];
            System.out.println("Reserva " + r.getId() + " | " + r.getHospede().getNome()
                    + " | Quarto " + r.getQuarto().getNumero()
                    + " | " + r.getDataEntrada() + " a " + r.getDataSaida()
                    + " | R$ " + r.getValorTotal() + " | " + r.getStatus());
        }
    }

    private Reserva buscarReserva(int id) throws ReservaException {
        for (int i = 0; i < dados.getTotalReservas(); i++) {
            Reserva r = dados.getReservas()[i];
            if (r.getId() == id) return r;
        }
        throw new ReservaException("Reserva nao encontrada!");
    }

    public void realizarCheckIn(int id) throws ReservaException {
        Reserva r = buscarReserva(id);
        if (!r.getStatus().equals("RESERVADA")) {
            throw new ReservaException("Check-in nao permitido para esta reserva!");
        }
        r.realizarCheckIn();
        persistencia.salvar(dados);
        System.out.println("Check-in realizado!");
    }

    public void realizarCheckOut(int id) throws ReservaException {
        Reserva r = buscarReserva(id);
        if (!r.getStatus().equals("EM ESTADIA")) {
            throw new ReservaException("O check-in deve ser feito antes do check-out!");
        }
        r.setStatus("FINALIZADA");
        persistencia.salvar(dados);
        System.out.println("Check-out realizado!");
    }

    public double calcularFaturamento() {
        double total = 0;
        for (int i = 0; i < dados.getTotalReservas(); i++) {
            Reserva r = dados.getReservas()[i];
            if (r.getStatus().equals("FINALIZADA")) total += r.getValorTotal();
        }
        return total;
    }
}
