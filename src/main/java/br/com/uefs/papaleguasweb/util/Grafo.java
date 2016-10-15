package br.com.uefs.papaleguasweb.util;


import br.com.uefs.papaleguasweb.exception.EncontraVerticeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Grafo {
        
    private List<Vertice> vertices;
    
    public Grafo(){
        vertices = new ArrayList<>();
    }
    
    public void insereVertice(Object o) throws EncontraVerticeException{
        Vertice v = encontraVertice(o);
        if(v==null){
            v = new Vertice(o);
            vertices.add(v);
        }else{
            throw new EncontraVerticeException();
        }
    }
    
    public void insereAresta(Object bairroOrigem, Object bairroDestino, String distancia, String tempo) throws EncontraVerticeException{
        Vertice origem = encontraVertice(bairroOrigem);
        Vertice destino = encontraVertice(bairroDestino);
        
        if(origem==null /*|| destino == null*/){
            throw new EncontraVerticeException();
        }else{
            Aresta aresta;
            if(!bairroOrigem.equals(bairroDestino)){
                aresta = new Aresta((String)bairroOrigem,(String)bairroDestino, distancia,tempo);
                origem.setAresta(aresta);
                aresta = new Aresta((String)bairroDestino,(String)bairroOrigem, distancia,tempo);
                destino.setAresta(aresta);
            }else{
                aresta = new Aresta((String)bairroOrigem,(String)bairroDestino, distancia,tempo);
                origem.setAresta(aresta);
            }
        }
    
    }
    
    public int qtdVertices(){
        return vertices.size();
    }
    
    public Iterator iteradorGrafo(){
        return vertices.iterator();
    }
    
    public List<String> getVertices(){
        List<String> vert = new ArrayList<>();
        
        for(Vertice v:vertices){
            vert.add(v.toString());
        }
        
        return vert;
    }
    
    public int[][] matriz(){
        int[][] intMtr = new int[vertices.size()][vertices.size()];
        int i=0,j;
        for(Vertice v:vertices){
            for(Aresta a:v.getArestas()){
                j=0;
                for(Vertice v2:vertices){
                    if(a.getDestino().equals(v2.getIdentificador()))
                        break;
                    j++;
                }
                intMtr[i][j]=1;
            }
            i++;
        }
        return intMtr;
    }
    
    //Função que determina o menor caminho possível entre vértices - a “Condicao” servirá para indicar se o caminho a calcular pelo tempo (true) ou distancia(false)
    public MenorCaminho menorCaminho(String bairroOrigem, String bairroDestino, boolean condicao){
        
        //Encontrando os vertices referentes aos parametros passados
        Vertice origem = encontraVertice(bairroOrigem);
        Vertice destino = encontraVertice(bairroDestino);
        MenorCaminho menorC = new MenorCaminho();
                  
	List<String> menorCaminho = new ArrayList<>();//lista com os vertices de menor caminho
        
        List<Vertice> vizitados = new ArrayList<>();//Lista dos vizitados
        List<String> anteriores = new ArrayList<>();//Lista de vertices anteriores.
        List<Double> distVert = new ArrayList<>();//lista das distancias dos vertices
        List<Double> tempVert = new ArrayList<>();//lista dos tempos dos vertice
        double infinito = 9999;
        double totalCaminho;
        
        
        if (origem.getIdentificador().equals(destino.getIdentificador()))
            throw new IllegalArgumentException("Cidade de origem igual 'a cidade de destino");
        
        Vertice atual = origem;//inicialmente o vertice atual é o vertice origem
                
        menorCaminho.add((String)origem.getIdentificador());//O menor caminho começa com a origem
        
        //inicializa todas os vertices
        for(Vertice v:vertices){
            if (v.getIdentificador().equals(atual.getIdentificador())) {
                tempVert.add(0.0);
                distVert.add(0.0);
                anteriores.add(vertices.indexOf(atual), (String)atual.getIdentificador()); //Seta o atual na lista de anteriores
            }else{
                tempVert.add(infinito);
                distVert.add(infinito);        //o tempo a cada vértice é infinito por defeito
                anteriores.add("");
            }
        }
        
        //procura os vertices vizinhos à origem
        List<Aresta> adjacentes = atual.getArestas();
        vizitados.add(atual); //coloca o vertice atual na lista de vizitados
                
        //caso não existam vertices vizinhos, não existe nenhum caminho possível

        if (adjacentes == null)
            throw new IllegalArgumentException("Nao existe nenhum caminho possivel");
        else{ //caso contrário, os vizinhos são definidas como os vertices anteriores da origem            
            double somaTemp;
            double somaDist;
            //Inicia as listas de tempo e anteriores
            while(vizitados.size()!=vertices.size()){
                for (Aresta adj : adjacentes) {
                    Vertice v = encontraVertice(adj.getDestino());
                    if (!vizitados.contains(v)) {
                        int indiceV = vertices.indexOf(v);
                        somaTemp = tempVert.get(vertices.indexOf(atual))+(Integer.parseInt(adj.getTempo()));
                        somaDist = distVert.get(vertices.indexOf(atual))+(Integer.parseInt(adj.getDistancia()));
                        if(condicao){
                            if (somaTemp < tempVert.get(indiceV)) {
                                anteriores.set(indiceV, (String) atual.getIdentificador());
                                tempVert.set(indiceV, somaTemp);
                                distVert.set(indiceV, somaDist);
                            }
                        }else{
                            if (somaDist < distVert.get(indiceV)) {
                                anteriores.set(indiceV, (String) atual.getIdentificador());
                                distVert.set(indiceV, somaDist);
                                tempVert.set(indiceV, somaTemp);
                            }
                        }
                    }
                }

                //Aqui começa verdadeiramente o algoritmo

                //encontra o vertice adjacente de menor tempo
                double menor = infinito;
                for(Aresta adj:adjacentes){
                    Vertice aux = encontraVertice(adj.getDestino());
                    if(!vizitados.contains(aux)){
                        int indiceAux = vertices.indexOf(aux);
                        if(condicao){
                            if((Integer.parseInt(adj.getTempo()))>0 && tempVert.get(indiceAux)<menor){
                                atual = aux;
                                menor = tempVert.get(indiceAux);
                            }
                        }else{
                            if ((Integer.parseInt(adj.getDistancia())) > 0 && distVert.get(indiceAux) < menor) {
                                atual = aux;
                                menor = distVert.get(indiceAux);
                            }
                        }
                        
                    }
                }
                if(menor == infinito)
                    break;

                adjacentes = atual.getArestas(); //Vertice de menor tempo passa a ser meu atual
                vizitados.add(atual);
            }
            
            int pos = vertices.indexOf(destino);
            String antecessor = anteriores.get(pos);
            
            menorC.setDistancia(distVert.get(pos));
            menorC.setTempo(tempVert.get(pos));
            
            while(true){  
                menorCaminho.add(1,(String)vertices.get(pos).getIdentificador());
                if(antecessor.equals(bairroOrigem))
                    break;                
                Vertice aux = encontraVertice(antecessor);                
                pos = vertices.indexOf(aux);
                antecessor = anteriores.get(pos);
            }
        }
        
        menorC.setCaminho(menorCaminho);
        
        return menorC;
            
    }

    
    private Vertice encontraVertice(Object bairro){
        for(Vertice v:vertices){
            if(v.getIdentificador().equals(bairro)){
                return v;
            }
        }
        
        return null;
    }
    
    private Aresta encontraAresta(Vertice origem, Vertice destino){
        for(Vertice v:vertices){
            for(Aresta art:v.getArestas()){
                if (art.getDestino().equals(destino)) {
                    return art;
                }
            }
            
        }
        return null;
    }
}
