package br.com.uefs.papaleguasweb.util;

import java.util.ArrayList;
import java.util.List;

//Classe para criação do grafo
public class Vertice {
    private final Object nome;
    private final List<Aresta> arestas;
    
    public Vertice(Object o) {
        nome = o;
        arestas = new ArrayList<>();
    }
    
    //Metodo que retorna a lista de Arestas
    public List<Aresta> getArestas() {
        return arestas;
    }

    //Metodo para armazenar aresta
    public void setAresta(Aresta aresta) {
        this.arestas.add(aresta);
    }

    public Object getNome() {
        return nome;
    }

    public void removeAresta(Object destino) {
        for (Aresta a : arestas) {
            if (destino.equals(a.getDestino())) {
                arestas.remove(a);
            }
        }
    }

    //Metodo que retorna a quantidade de ligações do vertice
    public int qtdeAresta() {
        return arestas.size();
    }
    
    //Metodo que retorna os vertices vizinhos
    public List<Vertice> getVerticesVizinhos(){
        List<Vertice> vizinhos = new ArrayList<>();
        for(Aresta a:arestas)
            vizinhos.add(a.getDestino());
        return vizinhos;
    }

    @Override
    public String toString() {
        return nome.toString();
    }
}
