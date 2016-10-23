package br.com.uefs.papaleguasweb.util.jsons;

import java.io.Serializable;

//Classe para criar os links no GrafoJson
//Classe para converter Aresta em aresta no formato Json
class ArestaJson implements Serializable{
    private final int source;//Origem
    private final int target;//Destino
    private String cor;

    //Pode usar o construtor sem cor, ele adota cor padrão
    public ArestaJson(int source, int target) {
        this.source = source;
        this.target = target;
        this.cor = "blue";//cor padrão: azul
    }
    
    //Pode usar o construtor com a cor
    public ArestaJson(int source, int target, String cor) {
        this.source = source;
        this.target = target;
        this.cor = cor;
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
