package br.com.uefs.papaleguasweb.model;

//Classe modelo para Clientes
public class Cliente {
    private static int seguencia=0; //Variavel usada para auto-incremento dos id's
    private final int id;//identificador
    private String nome;
    
    //Construtor com o nome
    public Cliente(String nome) {
        this.id = seguencia++;
        this.nome = nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
