package br.com.uefs.papaleguasweb.model;

//Classe modelo para motoristas
public class Motorista { 
    private static int seguencia=0;//Atributo pasa auto-incremento dos id's
    private final int id; //identificador
    private final String nome;
    private int numeroCarro;
    private String bairro; //bairro onde se localiza

    //Construtor
    public Motorista(String nome, int numero, String bairro) {
        id=seguencia++;
        this.nome = nome;
        numeroCarro = numero;
        this.bairro = bairro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getNumeroCarro() {
        return numeroCarro;
    }

    public void setNumeroCarro(int numeroCarro) {
        this.numeroCarro = numeroCarro;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
