package br.com.uefs.papaleguasweb.util;

import java.util.ArrayList;
import java.util.List;

//Classe usada pra armazenar as informações do menor caminho
public class MenorCaminho {
    private List<String> caminho; //Lista de vertices do caminho
    private double distancia;//Distancia percorrida
    private double tempo;//Tempo gasto

    public MenorCaminho() {
        caminho = new ArrayList<>();
    }
    
    public List<String> getCaminho() {
        return caminho;
    }

    public void setCaminho(List<String> caminho) {
        this.caminho = caminho;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }
}