package br.com.uefs.papaleguasweb.util.jsons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Classe usada para converter o Grafo para Json
public class GrafoJson implements Serializable{
    
    private final List<VerticeJson> nodes;//Lista de nodes (vertices em formato Json)
    private final List<ArestaJson> links;//Lista de links (arestas em formato Json)

    public GrafoJson() {
        nodes = new ArrayList<>();
        links = new ArrayList<>();
    }

    public void setNode (int id, String nome, String cor){
        VerticeJson gv = new VerticeJson(id, nome, cor); //cria um vertice em formato json
        nodes.add(gv);//armazena na lista de nodes
    }
    
    public void setLink(int origem, int destino, String cor){
        ArestaJson ga = new ArestaJson(origem, destino, cor); //cria uma aresta em formato json
        links.add(ga);//armazena na lista de links
    }    

    public List<VerticeJson> getNodes() {
        return nodes;
    }

    public List<ArestaJson> getLinks() {
        return links;
    }
}
