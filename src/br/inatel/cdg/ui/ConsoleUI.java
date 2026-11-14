package br.inatel.cdg.ui;
import java.util.Scanner;
import br.inatel.cdg.service.HotelService;
import br.inatel.cdg.Exception.ReservaException;
import br.inatel.cdg.Exception.ValidacaoException;

public class ConsoleUI {

    private Scanner entrada;
    private HotelService hotelService;

    public ConsoleUI() {
        entrada = new Scanner(System.in);
        hotelService = new HotelService();
    }

    public void exibirMenu() {
        System.out.println("===== HOTELSTAY =====");
        System.out.println("1 - Cadastrar reserva");
        System.out.println("2 - Listar reservas");
        System.out.println("3 - Realizar check-in");
        System.out.println("4 - Realizar check-out");
        System.out.println("5 - Consultar faturamento");
        System.out.println("6 - Cadastrar hospede");
        System.out.println("7 - Listar quartos");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    public int lerOpcao() {
        int op;
        op = entrada.nextInt();
        return op;
    }

    public void iniciar() {

        int op = -1;

        while (op != 0) {
            exibirMenu();
            op = lerOpcao();

            switch (op) {
                case 1:
                    System.out.println("Digite o ID do hospede:");
                    int idHospede = entrada.nextInt();

                    System.out.println("Digite o numero do quarto:");
                    int numeroQuarto = entrada.nextInt();

                    System.out.println("Digite a data de entrada (dd/MM/aaaa):");
                    String dataEntrada = entrada.next();

                    System.out.println("Digite a data de saida (dd/MM/aaaa):");
                    String dataSaida = entrada.next();

                    System.out.println("Digite a quantidade de ocupantes:");
                    int quantidadeOcupantes = entrada.nextInt();

                    try {
                        hotelService.cadastrarReserva(idHospede, numeroQuarto,
                                dataEntrada, dataSaida, quantidadeOcupantes);
                    } catch (ValidacaoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    hotelService.listarReservas();
                    break;

                case 3:
                    System.out.println("Digite o ID da reserva:");
                    int idCheckIn = entrada.nextInt();
                    try{
                        hotelService.realizarCheckIn(idCheckIn);
                    } catch (ReservaException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Digite o ID da reserva:");
                    int idCheckOut = entrada.nextInt();
                    try{
                        hotelService.realizarCheckOut(idCheckOut);
                    }catch (ReservaException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Faturamento: R$ " + hotelService.calcularFaturamento());
                    break;

                case 0:
                    System.out.println("Encerrando o sistema");
                    break;

                case 6:
                    System.out.println("Digite o nome do hospede:");
                    String nome = entrada.next();
                    System.out.println("Digite o documento:");
                    String documento = entrada.next();
                    System.out.println("Digite o telefone:");
                    String telefone = entrada.next();
                    try {
                    hotelService.cadastrarHospede(nome, documento, telefone);
                    } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
                
                case 7:
                hotelService.listarQuartos();
                break;

                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }
}
