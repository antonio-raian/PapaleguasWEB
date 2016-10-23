package br.com.uefs.papaleguasweb.util;

//Classe para a criação de arestas
public class Aresta {

    private final Vertice origem,destino; //vertices de origem e destino
    private final double distancia;
    private int tempo;

    public Aresta(Vertice o, Vertice d, double distancia, int tempo) {
        origem = o;
        destino = d;
        this.distancia = distancia;
        this.tempo = tempo;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public int getTempo() {
        return tempo;
    }
    
    public void setTempo(int tempo){
        this.tempo = tempo;
    }

    @Override
    public String toString() {
        return "Destino: " + destino + "/Distancia: " + distancia + "/Tempo: " + tempo;
    }

}
