package br.com.uefs.papaleguasweb.model;

import java.util.Date;

//Classe modelo para todas as corridas
public class Corrida {
    private Date data;
    private String origem, destino, distancia, tempo;
    private double valor;
    private String cliente; //nome do cliente
    private String motorista;//nome do motorista
    private int numeroCarro;//numero do taxi

    //Construtor vazio
    public Corrida() {
    }

    //construtor pada todas as informações
    public Corrida(Date data,String cliente, String motorista,String origem, String destino, String distancia, String tempo, double valor, int numeroCarro) {
        this.data = data;
        this.cliente = cliente;
        this.motorista = motorista;
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
        this.tempo = tempo;
        this.valor = valor;
        this.numeroCarro = numeroCarro;
    }
   
    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public int getNumeroCarro() {
        return numeroCarro;
    }

    public void setNumeroCarro(int numeroCarro) {
        this.numeroCarro = numeroCarro;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}