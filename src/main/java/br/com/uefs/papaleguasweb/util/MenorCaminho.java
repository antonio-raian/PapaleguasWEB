package br.com.uefs.papaleguasweb.util;

import java.util.ArrayList;
import java.util.List;

public class MenorCaminho {
    private List<String> caminho;
    private double distancia;
    private double tempo;

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

    public MenorCaminho() {
        caminho = new ArrayList<>();
    }
}