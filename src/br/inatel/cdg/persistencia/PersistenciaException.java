package br.inatel.cdg.persistencia;

public class PersistenciaException extends RuntimeException {

    public PersistenciaException(String mensagem) {
        super(mensagem);
    }
}