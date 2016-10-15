package br.com.uefs.papaleguasweb.util;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private final Object identificador;
    private final List<Aresta> arestas;
    
    public Vertice(Object o) {
        identificador = o;
        arestas = new ArrayList<>();
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void setAresta(Aresta aresta) {
        this.arestas.add(aresta);
    }

    public Object getIdentificador() {
        return identificador;
    }

    public void removeAresta(Object destino) {
        for (Aresta a : arestas) {
            if (destino.equals(a.getDestino())) {
                arestas.remove(a);
            }
        }
    }

    public int qtdeAresta() {
        return arestas.size();
    }
    
    public List<String> getVerticesVizinhos(){
        List<String> vizinhos = new ArrayList<>();
        for(Aresta a:arestas)
            vizinhos.add(a.getDestino());
        return vizinhos;
    }

    @Override
    public String toString() {
        return identificador.toString();
    }
}
