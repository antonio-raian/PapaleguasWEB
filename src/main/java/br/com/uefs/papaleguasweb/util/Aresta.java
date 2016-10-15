package br.com.uefs.papaleguasweb.util;

public class Aresta {

    private final String origem,destino;
    private final String distancia, tempo;

    public Aresta(String o,String d, String distancia, String tempo) {
        origem = o;
        destino = d;
        this.distancia = distancia;
        this.tempo = tempo;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public String getDistancia() {
        return distancia;
    }

    public String getTempo() {
        return tempo;
    }

    @Override
    public String toString() {
        return "Destino: " + destino + "/Distancia: " + distancia + "/Tempo: " + tempo;
    }

}
