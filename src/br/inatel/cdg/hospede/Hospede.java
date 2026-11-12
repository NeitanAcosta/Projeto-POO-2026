package br.inatel.cdg.hospede;

import java.io.Serializable;

public class Hospede implements Serializable {

    private int id;
    private String nome;
    private String documento;
    private String telefone;

    public Hospede(int id, String nome, String documento, String telefone) {
        if (id <= 0 || nome == null || nome.isBlank()
                || documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("Dados do hospede invalidos.");
        }

        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void atualizarTelefone(String telefone) {
        this.telefone = telefone;
    }
}