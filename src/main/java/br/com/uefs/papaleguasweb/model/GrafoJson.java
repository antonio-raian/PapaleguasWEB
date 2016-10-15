package br.com.uefs.papaleguasweb.model;

import br.com.uefs.papaleguasweb.util.Aresta;
import br.com.uefs.papaleguasweb.util.Vertice;
import java.util.ArrayList;
import java.util.List;

public class GrafoJson {
    
    private List<Vertice> vert;
    private List<Aresta> arest;

    public GrafoJson() {
        vert = new ArrayList<>();
        arest = new ArrayList<>();
    }

    public List<Vertice> getVert() {
        return vert;
    }

    public void setVert(List<Vertice> vert) {
        this.vert = vert;
    }

    public List<Aresta> getArest() {
        return arest;
    }

    public void setArest(List<Aresta> arest) {
        this.arest = arest;
    }
}
