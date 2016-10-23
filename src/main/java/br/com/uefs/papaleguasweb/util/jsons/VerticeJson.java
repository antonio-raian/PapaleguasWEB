package br.com.uefs.papaleguasweb.util.jsons;

import java.io.Serializable;

//Classe para criar as Nodes do grafoJson
//Classe para converter os Vertices em Json
class VerticeJson implements Serializable{
    private final int id;
    private final String nome;
    private String cor;

    //pode usar o construtor sem passar a cor ele adota a cor padrão
    public VerticeJson(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.cor = "blue";//A cor por padrão é azul
    }
    //pode usar o construtor passando a cor
    public VerticeJson(int id, String nome, String cor) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
