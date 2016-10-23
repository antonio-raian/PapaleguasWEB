package br.com.uefs.papaleguasweb.util;


import br.com.uefs.papaleguasweb.util.jsons.GrafoJson;
import br.com.uefs.papaleguasweb.exception.EncontraVerticeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Grafo {
        
    private List<Vertice> vertices;
    
    public Grafo(){
        vertices = new ArrayList<>();
    }
    
    //Dado um valor, cria-se um vertice
    public void insereVertice(Object o) throws EncontraVerticeException{
        Vertice v = encontraVertice(o);//tenta encontrar o vertice
        if(v==null){//se não existir um vertice com a mesma informação
            v = new Vertice(o);//cria-se o vertice
            vertices.add(v);//adiciona na lista de vertices
        }else{
            throw new EncontraVerticeException(); //se existir vertice, retorna exceção
        }
    }
    
    //Dado dois valores de vertices, um de distancia e um de tempo, cria-se uma aresta
    public void insereAresta(Object vertOrigem, Object vertDestino, double distancia, int tempo) throws EncontraVerticeException{
        Vertice origem = encontraVertice(vertOrigem);//Tenta encontrar um vertice para o valor de origem
        Vertice destino = encontraVertice(vertDestino);//Tenta encontrar um vertice para o valor de destino
        
        if(origem==null || destino == null){//se não encontrar um dos dois lança exceção
            throw new EncontraVerticeException();
        }else{
            Aresta aresta = encontraAresta(destino.getNome().toString(), origem.getNome().toString()); //verifica se já existe uma aresta com tais informações
            if(aresta==null){ //se nao exixtir
                if(!vertOrigem.equals(vertDestino)){ //se a origem for diferente do destino
                    aresta = new Aresta(origem,destino, distancia,tempo); //cria uma aresta origem destino
                    origem.setAresta(aresta);//armazena na origem
                    aresta = new Aresta(destino,origem, distancia,tempo); //cria uma aresta destino origem
                    destino.setAresta(aresta);//armazena no destino
                    //Isso é necessário pois o grafo não é dirigido, ou seja, deve-se ser capaz de fazer o 
                    //caminho de origem a destino e de destino a origem
                }else{ //se a origem for igual ao destino
                    //A aresta é alto ligada
                    aresta = new Aresta(origem,destino, distancia,tempo);
                    origem.setAresta(aresta);
                }
            }
        }
    
    }
    
    //Retorna quantidade de Vertices
    public int qtdVertices(){
        return vertices.size();
    }
    
    //Retorna o iterador dos vertices do grafo
    public Iterator iteradorGrafo(){
        return vertices.iterator();
    }
    
    //Retorna a lista de nomes dos vertices
    public List<String> getVertices(){
        List<String> vert = new ArrayList<>();
        
        for(Vertice v:vertices){
            vert.add(v.toString());
        }
        
        return vert;
    }
    
    //Gera matriz de adjacencia
    public int[][] matriz(){
        int[][] intMtr = new int[vertices.size()][vertices.size()];
        int i=0,j;
        for(Vertice v:vertices){
            for(Aresta a:v.getArestas()){
                j=0;
                for(Vertice v2:vertices){
                    if(a.getDestino().equals(v2.getNome()))
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
        Vertice origem = encontraVertice(bairroOrigem+" Salvador Bahia");
        Vertice destino = encontraVertice(bairroDestino+" Salvador Bahia");
        MenorCaminho menorC = new MenorCaminho();
                  
	List<String> menorCaminho = new ArrayList<>();//lista com os vertices de menor caminho
        
        List<Vertice> vizitados = new ArrayList<>();//Lista dos vizitados
        List<String> anteriores = new ArrayList<>();//Lista de vertices anteriores.
        List<Double> distVert = new ArrayList<>();//lista das distancias dos vertices
        List<Double> tempVert = new ArrayList<>();//lista dos tempos dos vertice
        double infinito = 9999;
        
        
        if (origem.getNome().equals(destino.getNome()))
            throw new IllegalArgumentException("Cidade de origem igual 'a cidade de destino");
        
        Vertice atual = origem;//inicialmente o vertice atual é o vertice origem
                
        menorCaminho.add((String)origem.getNome());//O menor caminho começa com a origem
        
        //inicializa todas os vertices
        for(Vertice v:vertices){
            if (v.getNome().equals(atual.getNome())) {
                tempVert.add(0.0);
                distVert.add(0.0);
                anteriores.add(vertices.indexOf(atual), (String)atual.getNome()); //Seta o atual na lista de anteriores
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
            //Aqui começa verdadeiramente o algoritmo
            while(vizitados.size()!=vertices.size()){
                for (Aresta adj : adjacentes) {
                    Vertice v = encontraVertice(adj.getDestino().getNome()); //encontra o vertice de adjacencia
                    if (!vizitados.contains(v)) { //se não foi vizitado
                        int indiceV = vertices.indexOf(v); //Pega o indice do vertice
                        somaTemp = tempVert.get(vertices.indexOf(atual))+(adj.getTempo()); //soma o tempo da aresta com o vertice anterior
                        somaDist = distVert.get(vertices.indexOf(atual))+(adj.getDistancia());//soma a distancia da aresta com o vertice anterior
                        if(condicao){ //condição serve para calcular pelo tempo (se true)
                            if (somaTemp < tempVert.get(indiceV)) { //Se a soma do tempo for menor do que a anterior
                                anteriores.set(indiceV, (String) atual.getNome()); //Atualiza o anterior
                                tempVert.set(indiceV, somaTemp);//Atualiza o tempo
                                distVert.set(indiceV, somaDist);//Atualiza a distancia
                            }
                        }else{ //calcular pela distancia (se false)
                            if (somaDist < distVert.get(indiceV)) {//Se a soma da distancia for menor do que a anterior
                                anteriores.set(indiceV, (String) atual.getNome()); //Atualiza o anterior
                                distVert.set(indiceV, somaDist);//Atualiza o tempo
                                tempVert.set(indiceV, somaTemp);//Atualiza a distancia
                            }
                        }
                    }
                }

                double menor = infinito;
                //encontra o vertice adjacente de menor tempo
                for(Aresta adj:adjacentes){
                    Vertice aux = encontraVertice(adj.getDestino().getNome());
                    if(!vizitados.contains(aux)){
                        int indiceAux = vertices.indexOf(aux);
                        if(condicao){
                            if(adj.getTempo()>0 && tempVert.get(indiceAux)<menor){
                                atual = aux; //Vertice de menor tempo passa a ser meu atual
                                menor = tempVert.get(indiceAux);
                            }
                        }else{
                            if (adj.getDistancia() > 0 && distVert.get(indiceAux) < menor) {
                                atual = aux;//Vertice de menor distancia passa a ser meu atual
                                menor = distVert.get(indiceAux);
                            }
                        }
                        
                    }
                }
                if(menor == infinito) //se for infinito ele é o ultimo vertice
                    break;

                adjacentes = atual.getArestas(); //Pega os adjacentes do atual
                vizitados.add(atual); //Coloca o atual na lista de vizitados
            }
            
            //Aqui armazena as informações do menor caminho
            int pos = vertices.indexOf(destino);//pega a posição do destino
            String antecessor = anteriores.get(pos);//pega o anterior do destino
            menorCaminho.add(1,(String)vertices.get(pos).getNome());//adciona ao array de menor caminho o destino
            
            menorC.setDistancia(distVert.get(pos));//No objeto menor caminho armazena a distancia
            menorC.setTempo(tempVert.get(pos));//No objeto menor caminho armazena o tempo
            
            while(!menorCaminho.contains(antecessor)){ //enquanto o antecessor não for a origem, faz o caminho inverso
                Vertice aux = encontraVertice(antecessor);//encontra o anteessor                
                pos = vertices.indexOf(aux);//pega a posição dele
                menorCaminho.add(1,(String)vertices.get(pos).getNome());//adiciona na lista de menor caminho
                
                antecessor = anteriores.get(pos); //pega o antecessor do vertice aux
            }
        }
        
        menorC.setCaminho(menorCaminho);//Adiciona a lista de menor caminho do objeto Menor Caminho
        
        return menorC; //retorna o objeto
            
    }

    //A partir do grafo gera-se um objeto Json
    public GrafoJson getJson(MenorCaminho menor){
        GrafoJson graf = new GrafoJson(); //Objeto GrafoJson
        if(menor==null){ //Se o parametro for nulo, retorna o grafo como padrão
            //Padrão: Vertice azul, Aresta azul
            for(Vertice v:vertices){
                graf.setNode(vertices.indexOf(v), v.getNome().toString(), "blue");
                List<Aresta> adj = v.getArestas();
                for(Aresta a:adj){
                    graf.setLink(vertices.indexOf(a.getOrigem()), vertices.indexOf(a.getDestino()), "blue");
                }
            }
        }else{ //se não for nulo, retorna um grafo com os vertices do Menor Caminho pintados de vermelho
            //Padrão: vertices selecionados: Vermelho; Aresta selecionada: Vermelho
            for(Vertice v:vertices){
                if(menor.getCaminho().contains(v.getNome().toString())){//Se o vertice tiver entro os caminho  do menor caminho
                    graf.setNode(vertices.indexOf(v), v.getNome().toString(), "red");
                }else
                    graf.setNode(vertices.indexOf(v), v.getNome().toString(), "blue");
                List<Aresta> adj = v.getArestas();
                for(Aresta a:adj){
                    String ori = a.getOrigem().getNome().toString();
                    String des = a.getDestino().getNome().toString();
                    if(menor.getCaminho().contains(ori) && menor.getCaminho().contains(des)){ //se a aresta estiver ligando dois vertices selecionados
                        graf.setLink(vertices.indexOf(a.getOrigem()), vertices.indexOf(a.getDestino()), "red");
                    }else
                        graf.setLink(vertices.indexOf(a.getOrigem()), vertices.indexOf(a.getDestino()), "blue");
                }
            }
        }
        
        return graf;//retorna o grafo
    }
    
    //Metodo que dado um nome encontra o vertice correspondente
    private Vertice encontraVertice(Object vertice){
        for(Vertice v:vertices){
            if(v.getNome().equals(vertice)){
                return v; //se encontrar retorna o vertice
            }
        }
        
        return null;//retorna null caso não encontre
    }
    
    //Metodo que dado dois nomes, origem e destino, encontra uma aresta
    private Aresta encontraAresta(String origem, String destino){
        for(Vertice v:vertices){
            for(Aresta art:v.getArestas()){
                if (art.getOrigem().getNome().toString().equals(origem)&& art.getDestino().getNome().toString().equals(destino)) {
                    return art;//Se encontrar, retorna a aresta
                }
            }
            
        }
        return null; //restorna null caso não encontre
    }

    //Metodo que dado dois nomes, origem e destino, encontra uma aresta e altera o valor entre elas
    public void alteraTempo(String origem, String destino, int tempo){
        Aresta a = encontraAresta(origem, destino);
        if(a!=null)
            a.setTempo(tempo);
        
        a = encontraAresta(destino, origem);
        if(a!=null)
            a.setTempo(tempo);
    }

    //Metodo que recebe um nome e retorna os vertices adjacentes
    public List<String> getAdjacentes(String origem) {
        Vertice vert = encontraVertice(origem);
        List<Vertice> lista = vert.getVerticesVizinhos();
        List<String> str = new ArrayList<>();
        for(Vertice v:lista){
            str.add(v.getNome().toString());
        }
        return str;
    }
}
