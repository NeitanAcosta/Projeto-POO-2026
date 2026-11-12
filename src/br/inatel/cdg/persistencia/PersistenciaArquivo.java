package br.inatel.cdg.persistencia;

import br.inatel.cdg.Exception.PersistenciaException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersistenciaArquivo {

    public void salvar(HotelData dados) {
        try (ObjectOutputStream arquivo = new ObjectOutputStream(
                new FileOutputStream("hotel.dat"))) {

            arquivo.writeObject(dados);

        } catch (Exception e) {
            throw new PersistenciaException("Erro ao salvar os dados do hotel.");
        }
    }

    public HotelData carregar() {
        if (!new File("hotel.dat").exists()) {
            return new HotelData();
        }

        try (ObjectInputStream arquivo = new ObjectInputStream(
                new FileInputStream("hotel.dat"))) {

            return (HotelData) arquivo.readObject();

        } catch (Exception e) {
            throw new PersistenciaException("Erro ao carregar os dados do hotel.");
        }
    }
}