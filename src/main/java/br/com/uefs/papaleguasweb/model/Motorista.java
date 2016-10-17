package br.com.uefs.papaleguasweb.model;

import java.util.List;

public class Motorista {
    private static int id=0;
    private final String nome;
    private final String senha;
    
    private List<Corrida> corridas;

    public Motorista(String nome, String senha) {
        Motorista.id++;
        this.nome = nome;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public List<Corrida> getCorridas() {
        return corridas;
    }
    
    public void setCorrida(Corrida corrida){
        this.corridas.add(corrida);
    }
}
