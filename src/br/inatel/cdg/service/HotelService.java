
package br.inatel.cdg.service;
import br.inatel.cdg.reserva.Reserva;
import br.inatel.cdg.Exception.ReservaException;
import br.inatel.cdg.Exception.ValidacaoException;
import br.inatel.cdg.persistencia.HotelData;
import br.inatel.cdg.persistencia.PersistenciaArquivo;
import br.inatel.cdg.hospede.Hospede;
import br.inatel.cdg.quarto.Quarto;
import br.inatel.cdg.quarto.Standard;
import br.inatel.cdg.quarto.Luxo;
import br.inatel.cdg.quarto.Suite;

public class HotelService {
    private HotelData dados;
    private PersistenciaArquivo persistencia;
    private double faturamento;

    public HotelService() {
        persistencia = new PersistenciaArquivo();
        dados = persistencia.carregar();

        if (dados.getTotalHospedes() == 0) {
            cadastrarHospede("Joao Silva", "123456789", "11999999999");
            cadastrarHospede("Maria Souza", "987654321", "11988888888");
        }
        if(dados.getTotalQuartos() == 0) {
            cadastrarQuarto(101, 2, 150.0, "STANDARD");
            cadastrarQuarto(102, 3, 200.0, "LUXO");
            cadastrarQuarto(103, 4, 300.0, "SUITE");
        }
    }

    public void cadastrarHospede(Hospede hospede) {
        if (hospede == null) {
            throw new IllegalArgumentException("Hospede invalido!");
        }
        dados.adicionarHospede(hospede);
        persistencia.salvar(dados);
        System.out.println("Hospede cadastrado com sucesso!");
    }

    public void cadastrarHospede(String nome, String documento, String telefone) {
        int id = dados.gerarIdHospede();
        Hospede hospede = new Hospede(id, nome, documento, telefone);
        cadastrarHospede(hospede);
    }

    public Hospede buscarHospede(int id) {
        for (int i = 0; i < dados.getTotalHospedes(); i++) {
            if (dados.getHospedes()[i].getId() == id) {
                return dados.getHospedes()[i];
            }
        }
        return null;
    }

    public Quarto buscarQuarto(int numero) {
        for (int i = 0; i < dados.getTotalQuartos(); i++) {
            if (dados.getQuartos()[i].getNumero() == numero) {
                return dados.getQuartos()[i];
            }
        }
        return null;
    }

    public void cadastrarReserva(int idHospede, int numeroQuarto,
        String dataEntrada, String dataSaida,
        int quantidadeOcupantes, int diarias) throws ValidacaoException {
            Hospede hospede = buscarHospede(idHospede);
            Quarto quarto = buscarQuarto(numeroQuarto);
        if (hospede == null) {
            throw new ValidacaoException("Hospede nao encontrado!");
        }
        if (quarto == null) {
            throw new ValidacaoException("Quarto nao encontrado!");
        }
        if (!quarto.permiteOcupantes(quantidadeOcupantes)) {
            throw new ValidacaoException("Quantidade de ocupantes invalida!");
        }
        if (dataEntrada == null || dataEntrada.isEmpty()
            || dataSaida == null || dataSaida.isEmpty()) {
            throw new ValidacaoException("Datas invalidas!");
        }
        if (diarias <= 0) {
            throw new ValidacaoException("Quantidade de diarias invalida!");
        }
        int id = dados.gerarIdReserva();
        Reserva reserva = new Reserva(id, hospede, quarto,
        dataEntrada, dataSaida, quantidadeOcupantes);
        reserva.setValorTotal(quarto.calcularValor(diarias));
        cadastrarReserva(reserva);
    }

    public void cadastrarQuarto(Quarto quarto) {
        if (quarto == null) {
            throw new IllegalArgumentException("Quarto invalido!");
        }
        dados.adicionarQuarto(quarto);
        persistencia.salvar(dados);
        System.out.println("Quarto cadastrado com sucesso!");
    }

    public void cadastrarQuarto(int numero, int capacidade, double valorBase, String tipo) {
        Quarto quarto = null;
        switch (tipo) {
            case "STANDARD":
                quarto = new Standard(numero, capacidade, valorBase);
                break;
            case "LUXO":
                quarto = new Luxo(numero, capacidade, valorBase);
                break;
            case "SUITE":
                quarto = new Suite(numero, capacidade, valorBase);
                break;
            default:
                throw new IllegalArgumentException("Tipo de quarto invalido!");
            }
        cadastrarQuarto(quarto);
    }

    public void listarQuartos() {
        if (dados.getTotalQuartos() == 0) {
            System.out.println("Nenhum quarto cadastrado!");
        } else {
            for (int i = 0; i < dados.getTotalQuartos(); i++) {
                System.out.println("----------------------");
                System.out.println("Numero: " + dados.getQuartos()[i].getNumero());
                System.out.println("Capacidade: " + dados.getQuartos()[i].getCapacidade());
                System.out.println("Valor base: " + dados.getQuartos()[i].getValorBase());
                System.out.println("Tipo: " + dados.getQuartos()[i].getClass().getSimpleName());
            }
        }
    }

    public void cadastrarReserva(Reserva reserva) throws ValidacaoException {
        if (reserva == null) {
        throw new ValidacaoException("Reserva invalida!");
    }
        dados.adicionarReserva(reserva);
        persistencia.salvar(dados);
        System.out.println("Reserva cadastrada com sucesso!");
    }

    public void listarReservas() {
    if (dados.getTotalReservas() == 0) {
        System.out.println("Nenhuma reserva cadastrada!");
    } else {
        for (int i = 0; i < dados.getTotalReservas(); i++) {
            System.out.println("----------------------");
            System.out.println("ID da reserva: " + dados.getReservas()[i].getId());
            System.out.println("Hospede: " + dados.getReservas()[i].getHospede().getNome());
            System.out.println("Quarto: " + dados.getReservas()[i].getQuarto().getNumero());
            System.out.println("Data de entrada: " + dados.getReservas()[i].getDataEntrada());
            System.out.println("Data de saida: " + dados.getReservas()[i].getDataSaida());
            System.out.println("Quantidade de ocupantes: " + dados.getReservas()[i].getQuantidadeOcupantes());
            System.out.println("Valor total: " + dados.getReservas()[i].getValorTotal());
            System.out.println("Status: " + dados.getReservas()[i].getStatus());
            }
        }
    }

    public void realizarCheckIn(int id) throws ReservaException {
        for (int i = 0; i < dados.getTotalReservas(); i++) {
            if (dados.getReservas()[i].getId() == id) {
                switch (dados.getReservas()[i].getStatus()) {
                    case "RESERVADA":
                        dados.getReservas()[i].realizarCheckIn();
                        persistencia.salvar(dados);
                        System.out.println("Check-in realizado com sucesso!");
                        break;
                    case "EM ESTADIA":
                        throw new ReservaException("O check-in ja foi realizado!");
                    case "FINALIZADA":
                        throw new ReservaException("Essa reserva ja foi finalizada!");
                }
                return;
            }
        }
        throw new ReservaException("Reserva nao encontrada!");
    }   

    public void realizarCheckOut(int id) throws ReservaException {
        for (int i = 0; i < dados.getTotalReservas(); i++) {
            if (dados.getReservas()[i].getId() == id) {
                switch (dados.getReservas()[i].getStatus()) {
                    case "RESERVADA":
                        throw new ReservaException("O hospede ainda nao realizou o check-in!");
                    case "EM ESTADIA":
                        dados.getReservas()[i].setStatus("FINALIZADA");
                        persistencia.salvar(dados);
                        System.out.println("Check-out realizado com sucesso!");
                        break;
                    case "FINALIZADA":
                        throw new ReservaException("O check-out ja foi realizado!");
                }
                return;
            }
        }
        throw new ReservaException("Reserva nao encontrada!");
    }

    public double calcularFaturamento() {
        faturamento = 0;
        for (int i = 0; i < dados.getTotalReservas(); i++) {
            switch (dados.getReservas()[i].getStatus()) {
                case "FINALIZADA":
                    faturamento = faturamento + dados.getReservas()[i].getValorTotal();
                    break;
                }
            }
            return faturamento;
        }   

}
