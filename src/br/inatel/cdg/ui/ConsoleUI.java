package br.inatel.cdg.ui;
import java.util.Scanner;
import br.inatel.cdg.service.HotelService;

public class ConsoleUI{
    private Scanner entrada;
    private HotelService hotelService;

    public ConsoleUI{
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
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    public int lerOpcao() {
        int op;
        op = entrada.nextInt();
        return op;
    }


}